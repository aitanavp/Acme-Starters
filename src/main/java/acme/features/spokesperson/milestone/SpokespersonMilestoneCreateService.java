
package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.MilestoneKind;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

public class SpokespersonMilestoneCreateService extends AbstractService<Spokesperson, Milestone> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonMilestoneRepository	repository;

	private Milestone						milestone;

	private Campaign						campaign;

	// AbstractService interface -----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);

		status = this.campaign != null && this.campaign.getDraftMode() && this.campaign.getSpokesperson().isPrincipal();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int campaignId;

		if (this.campaign == null) {
			campaignId = super.getRequest().getData("campaignId", int.class);
			this.campaign = this.repository.findCampaignById(campaignId);
		}

		this.milestone = super.newObject(Milestone.class);
		this.milestone.setCampaign(this.campaign);
	}

	@Override
	public void bind() {
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.milestone);
	}

	@Override
	public void execute() {
		this.repository.save(this.milestone);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());
		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		tuple.put("kind", choices.getSelected().getKey());
		tuple.put("MilestoneKind", choices);

		super.unbindGlobal("campaignId", this.milestone.getCampaign().getId());
		super.unbindGlobal("draftMode", this.milestone.getCampaign().getDraftMode());
	}

}
