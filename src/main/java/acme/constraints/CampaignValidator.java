
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.CampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CampaignRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidCampaign annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {

		assert context != null;

		if (campaign == null)
			return true;

		else {
			{
				if (campaign.getTicker() != null) {
					Campaign existing = this.repository.findCampaignByTicker(campaign.getTicker());
					boolean uniqueTicker = existing == null || existing.getId() == campaign.getId();
					super.state(context, uniqueTicker, "ticker", "acme.validation.campaign.ticker.non-unique");
				}

			}
			{
				if (campaign.getDraftMode() != null && !campaign.getDraftMode()) {
					Long milestonesCount = this.repository.countMilestonesByCampaignId(campaign.getId());
					boolean hasMilestones = milestonesCount != null && milestonesCount > 0;

					super.state(context, hasMilestones, "draftMode", "acme.validation.campaign.milestones.message");
				}
			}
			{
				Date now = MomentHelper.getBaseMoment();
				Date start = campaign.getStartMoment();
				Date end = campaign.getEndMoment();
				boolean validDates = start != null && end != null && !MomentHelper.isBefore(start, now) && MomentHelper.isAfter(end, start);

				boolean validPublishedCampaign = campaign.getDraftMode() || validDates;

				super.state(context, validPublishedCampaign, "*", "acme.validation.campaign.dates.message");
			}
			Double monthsActive = campaign.getMonthsActive();
			boolean validMonths = monthsActive != null && monthsActive >= 0.0;

			super.state(context, validMonths, "monthsActive", "acme.validation.campaign.monthsActive.message");
		}

		return !super.hasErrors(context);
	}

}
