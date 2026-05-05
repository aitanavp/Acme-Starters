
package acme.features.projectMember.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberSponsorshipShowService extends AbstractService<ProjectMember, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberSponsorshipRepository	repository;

	private Sponsorship							sponsorship;

	// AbstractService interface ---------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		ProjectMember principal;

		principal = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		status = this.sponsorship != null && this.sponsorship.getProject() != null && principal != null
			&& this.repository.isProjectMemberInProject(this.sponsorship.getProject().getId(), principal.getId());
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney", "draftMode");
		super.unbindGlobal("sponsorId", this.sponsorship.getSponsor().getId());
	}

}
