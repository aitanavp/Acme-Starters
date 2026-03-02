
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
				Campaign existingCampaign = this.repository.findCampaignByTicker(campaign.getTicker());

				boolean uniqueCampaign = existingCampaign == null || existingCampaign.equals(campaign);

				super.state(context, uniqueCampaign, "ticker", "acme.validation.campaign.ticker.non-unique");

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
				boolean validDates = start != null && end != null && !start.before(now) && end.after(start);

				boolean validPublishedCampaign = campaign.getDraftMode() || validDates;

				super.state(context, validPublishedCampaign, "*", "acme.validation.campaign.dates.message");
			}
		}

		return !super.hasErrors(context);
	}

}
