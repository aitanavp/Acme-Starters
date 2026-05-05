
package acme.features.projectMember.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberCampaignShowService extends AbstractService<ProjectMember, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		ProjectMember principal;

		principal = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		status = this.campaign != null && this.campaign.getProject() != null && principal != null
			&& this.repository.isProjectMemberInProject(this.campaign.getProject().getId(), principal.getId());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "effort", "draftMode");
		super.unbindGlobal("spokespersonId", this.campaign.getSpokesperson().getId());
	}

}
