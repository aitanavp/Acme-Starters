package acme.features.projectMember.membership;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMembership;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberMembershipListService extends AbstractService<ProjectMember, ProjectMembership> {

	@Autowired
	private ProjectMemberMembershipRepository repository;

	private Collection<ProjectMembership> memberships;

	private Project project;


	@Override
	public void authorise() {
		boolean status;
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		status = this.project != null && (this.project.getManager().isPrincipal() || this.projectMembershipExistsForPrincipal(this.project.getId()));
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectMembership membership;
		ProjectMember managerMember;

		if (this.project == null) {
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
		}

		managerMember = this.repository.findProjectMemberByUserAccountId(this.project.getManager().getUserAccount().getId());
		if (managerMember != null && this.repository.findProjectMembershipByProjectIdAndProjectMemberId(this.project.getId(), managerMember.getId()) == null) {
			membership = super.newObject(ProjectMembership.class);
			membership.setProject(this.project);
			membership.setProjectMember(managerMember);
			this.repository.save(membership);
		}

		this.memberships = this.repository.findAllProjectMembershipsByProjectId(this.project.getId());
	}

	@Override
	public void unbind() {
		for (ProjectMembership membership : this.memberships) {
			Tuple tuple;

			tuple = super.unbindObject(membership, "projectMember");
			tuple.put("projectMember", membership.getProjectMember().getUserAccount().getUsername());
		}
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		super.unbindGlobal("canManageMembers", this.project.getDraftMode() && this.project.getManager().isPrincipal());
	}

	private boolean projectMembershipExistsForPrincipal(final int projectId) {
		ProjectMember principal;

		principal = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		return principal != null && this.repository.findProjectMembershipByProjectIdAndProjectMemberId(projectId, principal.getId()) != null;
	}

}