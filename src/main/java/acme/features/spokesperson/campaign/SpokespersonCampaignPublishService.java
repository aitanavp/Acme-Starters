
package acme.features.spokesperson.campaign;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.features.spokesperson.milestone.SpokespersonMilestoneRepository;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	@Autowired
	private SpokespersonMilestoneRepository	milestoneRepository;

	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Campaign campaign;

		id = super.getRequest().getData("id", int.class);
		campaign = this.repository.findCampaignById(id);

		status = campaign != null && campaign.getDraftMode() && campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);

		boolean hasMilestones = this.milestoneRepository.countByCampaignId(this.campaign.getId()) > 0;
		super.state(hasMilestones, "*", "spokesperson.campaign.publish.error.no-milestones");

		Date start = this.campaign.getStartMoment();
		Date end = this.campaign.getEndMoment();
		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "spokesperson.campaign.publish.error.end-after-start");

		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "spokesperson.campaign.publish.error.start-future");

		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "spokesperson.campaign.publish.error.end-future");
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "effort");
		super.unbindGlobal("spokespersonId", this.campaign.getSpokesperson().getId());
		super.unbindGlobal("draftMode", this.campaign.getDraftMode());
	}
}
