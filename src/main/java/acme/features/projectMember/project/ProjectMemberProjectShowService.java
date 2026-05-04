
package acme.features.projectMember.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberProjectShowService extends AbstractService<ProjectMember, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberProjectRepository	repository;

	private Project							project;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		boolean isManager = this.project != null && this.project.getManager().isPrincipal();
		boolean isMember = false;
		
		if (this.project != null) {
			ProjectMember principal = (ProjectMember) super.getRequest().getPrincipal().getActiveRealm();
			isMember = principal != null && this.repository.isProjectMemberInProject(this.project.getId(), principal.getId());
		}
		
		status = isManager || isMember;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "effort");
		super.unbindGlobal("maganerId", this.project.getManager().getId());
	}

}
