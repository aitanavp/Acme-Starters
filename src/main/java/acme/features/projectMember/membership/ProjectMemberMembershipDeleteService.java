package acme.features.projectMember.membership;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMembership;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberMembershipDeleteService extends AbstractService<ProjectMember, ProjectMembership> {

	@Autowired
	private ProjectMemberMembershipRepository repository;

	private ProjectMembership membership;

	private Project project;

	private Collection<ProjectMembership> memberships;


	@Override
	public void authorise() {
		boolean status;
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		status = this.project != null && this.project.getDraftMode() && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectMembership managerMembership;
		ProjectMember managerMember;

		if (this.project == null) {
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
		}

		managerMember = this.repository.findProjectMemberByUserAccountId(this.project.getManager().getUserAccount().getId());
		if (managerMember != null) {
			managerMembership = this.repository.findProjectMembershipByProjectIdAndProjectMemberId(this.project.getId(), managerMember.getId());
			if (managerMembership == null) {
				managerMembership = super.newObject(ProjectMembership.class);
				managerMembership.setProject(this.project);
				managerMembership.setProjectMember(managerMember);
				this.repository.save(managerMembership);
			}
		}

		this.membership = super.newObject(ProjectMembership.class);
		this.membership.setProject(this.project);
		this.memberships = this.repository.findAllProjectMembershipsByProjectId(this.project.getId());
	}

	@Override
	public void bind() {
		super.bindObject(this.membership, "projectMember");
	}

	@Override
	public void validate() {
		super.validateObject(this.membership);
		if (this.membership.getProjectMember() != null) {
			super.state(!this.isManagerMembership(this.membership.getProjectMember().getId()), "projectMember", "project-member.membership.form.error.manager");
			super.state(this.repository.findProjectMembershipByProjectIdAndProjectMemberId(this.project.getId(), this.membership.getProjectMember().getId()) != null, "projectMember", "project-member.membership.form.error.not-found");
		}
	}

	@Override
	public void execute() {
		ProjectMembership storedMembership;

		storedMembership = this.repository.findProjectMembershipByProjectIdAndProjectMemberId(this.project.getId(), this.membership.getProjectMember().getId());
		this.repository.delete(storedMembership);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		choices.add("0", "----", this.membership.getProjectMember() == null);
		for (ProjectMembership membership : this.memberships) {
			ProjectMember member;

			member = membership.getProjectMember();
			if (this.isManagerMembership(member.getId()))
				continue;
			choices.add(String.valueOf(member.getId()), member.getUserAccount().getUsername(), member.equals(this.membership.getProjectMember()));
		}

		tuple = super.unbindObject(this.membership, "projectMember");
		tuple.put("projectMember", choices.getSelected().getKey());
		tuple.put("projectMemberChoices", choices);

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

	private boolean isManagerMembership(final int projectMemberId) {
		ProjectMember managerMember;

		managerMember = this.repository.findProjectMemberByUserAccountId(this.project.getManager().getUserAccount().getId());
		return managerMember != null && managerMember.getId() == projectMemberId;
	}

}