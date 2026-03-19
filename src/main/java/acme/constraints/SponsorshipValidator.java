
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.datatypes.Money;
import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipRepository;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorshipRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {

		assert context != null;

		if (sponsorship == null)
			return true;

		else {
			{
				if (sponsorship.getTicker() != null) {
					Sponsorship existing = this.repository.findSponsorshipByTicker(sponsorship.getTicker());
					boolean uniqueTicker = existing == null || existing.getId() == sponsorship.getId();
					super.state(context, uniqueTicker, "ticker", "acme.validation.sponsorship.ticker.non-unique");
				}

			}
			{
				if (sponsorship.getDraftMode() != null && !sponsorship.getDraftMode()) {
					Long donationsCount = this.repository.countDonationsBySponsorshipId(sponsorship.getId());
					boolean hasDonations = donationsCount != null && donationsCount > 0;

					super.state(context, hasDonations, "draftMode", "acme.validation.sponsorship.donations.message");
				}
			}
			{
				Date now = MomentHelper.getBaseMoment();
				Date start = sponsorship.getStartMoment();
				Date end = sponsorship.getEndMoment();

				boolean validDates = start != null && end != null && !MomentHelper.isBefore(start, now) && MomentHelper.isAfter(end, start);
				boolean validPublishedSponsorship = sponsorship.getDraftMode() || validDates;

				super.state(context, validPublishedSponsorship, "*", "acme.validation.sponsorship.dates.message");
			}
			{
				Double monthsActive = sponsorship.getMonthsActive();
				boolean validMonths = Boolean.TRUE.equals(sponsorship.getDraftMode()) || monthsActive >= 0.0;

				super.state(context, validMonths, "monthsActive", "acme.validation.sponsorship.monthsActive.message");
			}
			{
				Money totalMoney = sponsorship.getTotalMoney();
				boolean validTotalMoney = totalMoney != null && totalMoney.getAmount() != null && totalMoney.getAmount() >= 0.0 && "EUR".equals(totalMoney.getCurrency());

				super.state(context, validTotalMoney, "totalMoney", "acme.validation.sponsorship.totalMoney.message");
			}
		}

		return !super.hasErrors(context);
	}

}
