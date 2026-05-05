
package acme.features.projectMember.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberCampaignListService extends AbstractService<ProjectMember, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberCampaignRepository	repository;

	private Collection<Campaign>			campaigns;

	private Project							project;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int projectId;

		if (this.project == null) {
			projectId = super.getRequest().getData("projectId", int.class);
			this.project = this.repository.findProjectById(projectId);
		}

		this.campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "effort", "draftMode");
	}

}
