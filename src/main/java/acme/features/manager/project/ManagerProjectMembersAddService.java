
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
import acme.realms.ProjectMember;

@Service
public class ManagerProjectMembersAddService extends AbstractService<Manager, Project> {

	@Autowired
	private ProjectMemberMembershipRepository	repository;

	private Project								project;
	private Collection<UserAccount>				candidates;
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

		this.candidates = this.repository.findAvailableUserAccountsByProjectId(this.project.getId());
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
			boolean duplicate = this.repository.findProjectMembershipByProjectMemberUserAccountIdAndProjectId(this.selectedUserAccount.getId(), this.project.getId()) != null;
			super.state(!duplicate, "projectMember", "project-member.membership.form.error.duplicate");
		}
	}

	@Override
	public void execute() {
		ProjectMember member;
		ProjectMembership membership;

		member = this.repository.findProjectMemberByUserAccountId(this.selectedUserAccount.getId());
		if (member == null) {
			member = super.newObject(ProjectMember.class);
			member.setUserAccount(this.selectedUserAccount); // <- esto faltaba
			this.repository.save(member);
		}

		membership = super.newObject(ProjectMembership.class);
		membership.setProject(this.project);
		membership.setProjectMember(member);
		this.repository.save(membership);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		choices.add("0", "----", this.selectedUserAccount == null);
		for (UserAccount ua : this.candidates)
			choices.add(String.valueOf(ua.getId()), ua.getUsername(), false);

		tuple = new Tuple();
		tuple.put("projectMember", choices.getSelected().getKey());
		tuple.put("projectMemberChoices", choices);
		super.getResponse().addData(tuple);

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}
}
