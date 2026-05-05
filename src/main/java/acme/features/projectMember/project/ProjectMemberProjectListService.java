
package acme.features.projectMember.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberProjectListService extends AbstractService<ProjectMember, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberProjectRepository	repository;

	private Collection<Project>				projects;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		ProjectMember projectMember;
		int id;

		projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		id = projectMember.getId();
		this.projects = this.repository.findProjectsByProjectMemberId(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "effort");
	}

}
