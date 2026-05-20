
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.UserAccount;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMembership;
import acme.features.projectMember.membership.ProjectMemberMembershipRepository;
import acme.realms.Manager;

@Service
public class ManagerProjectMembersDeleteService extends AbstractService<Manager, Project> {

	@Autowired
	private ProjectMemberMembershipRepository	repository;

	private Project								project;
	private Collection<ProjectMembership>		memberships;
	private UserAccount							selectedUserAccount;


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
		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));

		this.memberships = this.repository.findAllProjectMembershipsByProjectId(this.project.getId());
	}

	@Override
	public void bind() {
		int userAccountId = super.getRequest().getData("projectMember", int.class);
		this.selectedUserAccount = this.repository.findUserAccountById(userAccountId);
	}

	@Override
	public void validate() {
		if (this.selectedUserAccount == null)
			super.state(false, "projectMember", "project-member.membership.form.error.not-found");
		else {
			boolean exists = this.repository.findProjectMembershipByProjectMemberUserAccountIdAndProjectId(this.selectedUserAccount.getId(), this.project.getId()) != null;
			super.state(exists, "projectMember", "project-member.membership.form.error.not-found");
		}
	}

	@Override
	public void execute() {
		ProjectMembership storedMembership;

		storedMembership = this.repository.findProjectMembershipByProjectMemberUserAccountIdAndProjectId(this.selectedUserAccount.getId(), this.project.getId());
		this.repository.delete(storedMembership);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		choices.add("0", "----", this.selectedUserAccount == null);
		for (ProjectMembership membership : this.memberships) {
			UserAccount ua = membership.getProjectMember().getUserAccount();
			choices.add(String.valueOf(ua.getId()), ua.getUsername(), false);
		}

		tuple = new Tuple();
		tuple.put("projectMember", choices.getSelected().getKey());
		tuple.put("projectMemberChoices", choices);
		super.getResponse().addData(tuple);

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}
}
