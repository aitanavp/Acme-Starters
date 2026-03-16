
package acme.features.spokesperson.milestone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class SpokespersonMilestoneListService extends AbstractService<Spokesperson, Milestone> {

	// Internal state

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private List<Milestone>					milestones;

	private Campaign						campaign;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);

		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int campaignId;

		if (this.campaign == null) {
			campaignId = super.getRequest().getData("campaignId", int.class);
			this.campaign = this.repository.findCampaignById(campaignId);
		}

		this.milestones = this.repository.findMilestonesByCampaignId(this.campaign.getId());
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.milestones, "title", "achievements", "effort", "kind");
		super.unbindGlobal("campaignId", this.campaign.getId());
		super.unbindGlobal("draftMode", this.campaign.getDraftMode());
	}

}
