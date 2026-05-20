
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMembership;
import acme.features.projectMember.membership.ProjectMemberMembershipRepository;
import acme.realms.Manager;

@Service
public class ManagerProjectMembersListService extends AbstractService<Manager, Project> {

	@Autowired
	private ProjectMemberMembershipRepository	repository;

	private Collection<ProjectMembership>		memberships;
	private Project								project;


	@Override
	public void authorise() {
		boolean status;
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		status = this.project != null && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));

		this.memberships = this.repository.findAllProjectMembershipsByProjectId(this.project.getId());
	}

	@Override
	public void unbind() {
		for (ProjectMembership membership : this.memberships) {
			Tuple tuple = new Tuple();
			tuple.put("id", membership.getId());
			tuple.put("version", membership.getVersion());
			tuple.put("projectMember", membership.getProjectMember().getUserAccount().getUsername());
			super.getResponse().addData(tuple);
		}

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		super.unbindGlobal("canManageMembers", this.project.getDraftMode() && this.project.getManager().isPrincipal());
	}
}
