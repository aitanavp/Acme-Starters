
package acme.features.spokesperson.campaign;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignCreateService extends AbstractService<Spokesperson, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		Spokesperson spokesperson = (Spokesperson) super.getRequest().getPrincipal().getActiveRealm();
		this.campaign = super.newObject(Campaign.class);
		this.campaign.setDraftMode(true);
		this.campaign.setSpokesperson(spokesperson);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		Spokesperson spokesperson = (Spokesperson) super.getRequest().getPrincipal().getActiveRealm();
		this.campaign.setSpokesperson(spokesperson);
		this.campaign.setDraftMode(true);
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
		Date start = this.campaign.getStartMoment();
		Date end = this.campaign.getEndMoment();
		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "any.campaign.form.error.end-after-start");

		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "any.campaign.form.error.start-future");

		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "any.campaign.form.error.end-future");
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "effort");
	}

}
