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
public class ProjectMemberMembershipAddService extends AbstractService<ProjectMember, ProjectMembership> {

	@Autowired
	private ProjectMemberMembershipRepository repository;

	private ProjectMembership membership;

	private Project project;

	private Collection<ProjectMember> candidates;


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
		this.candidates = this.repository.findAvailableProjectMembersByProjectId(this.project.getId());
	}

	@Override
	public void bind() {
		super.bindObject(this.membership, "projectMember");
	}

	@Override
	public void validate() {
		super.validateObject(this.membership);

		if (this.membership.getProjectMember() != null) {
			boolean duplicate;

			duplicate = this.repository.findProjectMembershipByProjectIdAndProjectMemberId(this.project.getId(), this.membership.getProjectMember().getId()) != null;
			super.state(!duplicate, "projectMember", "project-member.membership.form.error.duplicate");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.membership);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		choices.add("0", "----", this.membership.getProjectMember() == null);
		for (ProjectMember candidate : this.candidates) {
			String key;
			String label;

			key = String.valueOf(candidate.getId());
			label = candidate.getUserAccount().getUsername();
			choices.add(key, label, candidate.equals(this.membership.getProjectMember()));
		}

		tuple = super.unbindObject(this.membership, "projectMember");
		tuple.put("projectMember", choices.getSelected().getKey());
		tuple.put("projectMemberChoices", choices);

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}