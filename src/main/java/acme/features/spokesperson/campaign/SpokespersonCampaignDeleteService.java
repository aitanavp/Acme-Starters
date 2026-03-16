
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignDeleteService extends AbstractService<Spokesperson, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
		status = this.campaign != null && this.campaign.getDraftMode() && this.campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		this.campaign.setSpokesperson(this.campaign.getSpokesperson());
		this.campaign.setDraftMode(true);
	}

	@Override
	public void validate() {
		// No additional validation needed
	}

	@Override
	public void execute() {
		Collection<Milestone> milestones = this.repository.findMilestonesByCampaignId(this.campaign.getId());
		this.repository.deleteAll(milestones);
		this.repository.delete(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "effort", "draftMode");
		super.unbindGlobal("spokespersonId", this.campaign.getSpokesperson().getId());
	}

}
