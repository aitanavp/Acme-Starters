
package acme.features.projectMember.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.Inventor;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberInventionListService extends AbstractService<ProjectMember, Invention> {

	@Autowired
	private ProjectMemberInventionRepository	repository;
	private Collection<Invention>				inventions;
	private Collection<Invention>				candidates;
	private Project								project;


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
		status = this.project != null && projectMember != null && this.repository.isProjectMemberInProject(this.project.getId(), projectMember.getId());
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Inventor inventor;

		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
		if (this.project == null)
			return;
		this.inventions = this.repository.findInventionsByProjectId(this.project.getId());
		try {
			inventor = (Inventor) super.getRequest().getPrincipal().getRealmOfType(Inventor.class);
		} catch (final Throwable e) {
			inventor = null;
		}
		if (inventor != null)
			this.candidates = this.repository.findAvailableInventionsByInventorId(inventor.getId(), this.project.getId());
	}

	@Override
	public void unbind() {
		if (this.inventions == null)
			return;
		super.unbindObjects(this.inventions, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost", "draftMode");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		super.unbindGlobal("showCreate", this.candidates != null && !this.candidates.isEmpty());
	}
}
