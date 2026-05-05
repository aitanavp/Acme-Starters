
package acme.features.projectMember.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberInventionShowService extends AbstractService<ProjectMember, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberInventionRepository	repository;

	private Invention							invention;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		ProjectMember projectMember;

		projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		status = this.invention != null && this.invention.getProject() != null && projectMember != null
			&& this.repository.isProjectMemberInProject(this.invention.getProject().getId(), projectMember.getId());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "cost");
		super.unbindGlobal("inventorId", this.invention.getInventor().getId());
	}

}
