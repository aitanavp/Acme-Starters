package acme.features.projectMember.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.Spokesperson;
import acme.realms.ProjectMember;
@Service
public class ProjectMemberCampaignCreateService extends AbstractService<ProjectMember, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberCampaignRepository	repository;

	private Project							project;

	private Campaign						campaign;

	private Collection<Campaign>			availableCampaigns;

	// AbstractService interface ----------------------------------------------

	@Override
	public void authorise() {
	    boolean status;
	    int projectId;
	    ProjectMember projectMember;
	    Spokesperson Spokesperson;

	    projectId = super.getRequest().getData("projectId", int.class);
	    this.project = this.repository.findProjectById(projectId);

	    try {
	        projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
	    } catch (final Throwable e) {
	        projectMember = null;
	    }

	    try {
	        Spokesperson = (Spokesperson) super.getRequest().getPrincipal().getRealmOfType(Spokesperson.class);
	    } catch (final Throwable e) {
	        Spokesperson = null;
	    }

	    status = this.project != null && this.project.getDraftMode()
	        && projectMember != null && Spokesperson != null
	        && this.repository.isProjectMemberInProject(this.project.getId(), projectMember.getId());

	    super.setAuthorised(status);
	}

	@Override
	public void load() {
		Spokesperson spokesperson;

		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));

		spokesperson = (Spokesperson) super.getRequest().getPrincipal().getRealmOfType(Spokesperson.class);

		this.availableCampaigns = this.repository.findAvailableCampaignsBySpokespersonId(spokesperson.getId(), this.project.getId());

		this.campaign = null;
	}

	@Override
	public void bind() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = campaignId == 0 ? null : this.repository.findCampaignById(campaignId);
	}

	@Override
	public void validate() {
		Spokesperson spokesperson;

		spokesperson = (Spokesperson) super.getRequest().getPrincipal().getRealmOfType(Spokesperson.class);

		super.state(this.campaign != null, "campaignId", "project-member.campaign.form.error.required");
		if (this.campaign != null) {
			boolean isOwnCampaign;
			boolean isUnassigned;

			isOwnCampaign = this.campaign.getSpokesperson() != null
				&& this.campaign.getSpokesperson().getId() == spokesperson.getId();
			isUnassigned = this.campaign.getProject() == null;

			super.state(isOwnCampaign, "campaignId", "project-member.campaign.form.error.owner");
			super.state(isUnassigned, "campaignId", "project-member.campaign.form.error.assigned");
		}
	}

	@Override
	public void execute() {
		this.campaign.setProject(this.project);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		choices.add("0", "----", this.campaign == null);
		for (Campaign availableCampaign : this.availableCampaigns) {
			String key;
			String label;

			key = String.valueOf(availableCampaign.getId());
			label = String.format("%s - %s", availableCampaign.getTicker(), availableCampaign.getName());
			choices.add(key, label, availableCampaign.equals(this.campaign));
		}
		if (this.campaign != null && !this.availableCampaigns.contains(this.campaign))
			choices.add(
				String.valueOf(this.campaign.getId()),
				String.format("%s - %s", this.campaign.getTicker(), this.campaign.getName()),
				true
			);

		tuple = new Tuple();
		tuple.put("campaignId", choices.getSelected().getKey());
		tuple.put("campaignChoices", choices);

		super.getResponse().addData(tuple);

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}