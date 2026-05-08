package acme.features.projectMember.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.ProjectMember;
import acme.realms.Spokesperson;

@Service
public class ProjectMemberCampaignListService extends AbstractService<ProjectMember, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberCampaignRepository	repository;

	private Collection<Campaign>			campaigns;

	private Collection<Campaign>			candidates;

	private Project							project;

	// AbstractService interface ---------------------------------------------

	@Override
	public void authorise() {
		boolean status;
		int projectId;
		ProjectMember projectMember;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);

		try {
			projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		} catch (final Throwable e) {
			projectMember = null;
		}

		status = this.project != null && projectMember != null
			&& this.repository.isProjectMemberInProject(this.project.getId(), projectMember.getId());

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Spokesperson spokesperson;
 
		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
 
		this.campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
 
		try {
			spokesperson = (Spokesperson) super.getRequest().getPrincipal().getRealmOfType(Spokesperson.class);
		} catch (final Throwable e) {
			spokesperson = null;
		}
 
		if (spokesperson != null)
			this.candidates = this.repository.findAvailableCampaignsBySpokespersonId(spokesperson.getId(), this.project.getId());
	}


	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "effort", "draftMode");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		super.unbindGlobal("showCreate", this.candidates != null && !this.candidates.isEmpty());
	}

}