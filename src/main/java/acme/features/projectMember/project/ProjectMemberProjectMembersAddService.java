
package acme.features.projectMember.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.models.Errors;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.SpringHelper;
import acme.internals.helpers.ErrorsHelper;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import acme.client.services.AbstractService;
import acme.client.components.basis.AbstractEntity;
import acme.internals.helpers.BinderHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMembership;
import acme.features.projectMember.membership.ProjectMemberMembershipRepository;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberProjectMembersAddService extends AbstractService<ProjectMember, Project> {

	@Autowired
	private ProjectMemberMembershipRepository	repository;

	private ProjectMembership					membership;

	private Project								project;

	private Collection<ProjectMember>			candidates;


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

		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));

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
		org.springframework.data.util.Pair<ProjectMembership, Errors> pair;
		pair = super.bindObject(ProjectMembership.class, "projectMember");
		this.membership = pair.getFirst();
		this.membership.setProject(this.project);
		// merge local errors into global response errors
		Errors local = pair.getSecond();
		Errors global = super.getResponse().getErrors();
		for (java.util.Map.Entry<String, java.util.Set<String>> e : local)
			for (String m : e.getValue())
				global.add(e.getKey(), m);
	}

	@Override
	public void validate() {
		// Use Spring validator to validate the membership object and transfer errors
		if (this.membership != null) {
			Validator validator = SpringHelper.getValidator();
			String key = acme.client.helpers.StringHelper.toIdentity(this.membership);
			BeanPropertyBindingResult binding = new BeanPropertyBindingResult(this.membership, key);
			validator.validate(this.membership, binding);
			ErrorsHelper.transferErrors(binding, super.getResponse().getErrors());

			if (this.membership.getProjectMember() != null) {
				boolean duplicate;

				duplicate = this.repository.findProjectMembershipByProjectIdAndProjectMemberId(this.project.getId(), this.membership.getProjectMember().getId()) != null;
				super.state(!duplicate, "projectMember", "project-member.membership.form.error.duplicate");
			}
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

		tuple = new Tuple();
		// build inclusions similar to AbstractService.buildInclusions
		List<String> buffer = new ArrayList<>(Arrays.asList("projectMember"));
		if (this.membership instanceof AbstractEntity) {
			if (!buffer.contains("id"))
				buffer.add("id");
			if (!buffer.contains("version"))
				buffer.add("version");
		}
		String[] inclusions = buffer.toArray(new String[0]);
		BinderHelper.unbind(this.membership, super.getResponse().getErrors(), super.getRequest().getData(), tuple, inclusions);
		tuple.put("projectMember", choices.getSelected().getKey());
		tuple.put("projectMemberChoices", choices);
		super.getResponse().addData(tuple);

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}
