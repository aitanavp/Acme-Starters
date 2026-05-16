package acme.features.manager.project;

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
import acme.realms.Manager;
import acme.features.projectMember.membership.ProjectMemberMembershipRepository;

@Service
public class ManagerProjectMembersDeleteService extends AbstractService<Manager, Project> {

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
        acme.realms.ProjectMember managerMember;

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
        org.springframework.data.util.Pair<ProjectMembership, Errors> pair;
        pair = super.bindObject(ProjectMembership.class, "projectMember");
        this.membership = pair.getFirst();
        this.membership.setProject(this.project);
        Errors local = pair.getSecond();
        Errors global = super.getResponse().getErrors();
        for (java.util.Map.Entry<String, java.util.Set<String>> e : local)
            for (String m : e.getValue())
                global.add(e.getKey(), m);
    }

    @Override
    public void validate() {
        if (this.membership != null) {
            Validator validator = SpringHelper.getValidator();
            String key = acme.client.helpers.StringHelper.toIdentity(this.membership);
            BeanPropertyBindingResult binding = new BeanPropertyBindingResult(this.membership, key);
            validator.validate(this.membership, binding);
            ErrorsHelper.transferErrors(binding, super.getResponse().getErrors());
            if (this.membership.getProjectMember() != null) {
                super.state(!this.isManagerMembership(this.membership.getProjectMember().getId()), "projectMember", "project-member.membership.form.error.manager");
                super.state(this.repository.findProjectMembershipByProjectIdAndProjectMemberId(this.project.getId(), this.membership.getProjectMember().getId()) != null, "projectMember", "project-member.membership.form.error.not-found");
            }
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
            acme.realms.ProjectMember member;

            member = membership.getProjectMember();
            if (this.isManagerMembership(member.getId()))
                continue;
            choices.add(String.valueOf(member.getId()), member.getUserAccount().getUsername(), member.equals(this.membership.getProjectMember()));
        }

        tuple = new Tuple();
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

    private boolean isManagerMembership(final int projectMemberId) {
        acme.realms.ProjectMember managerMember;

        managerMember = this.repository.findProjectMemberByUserAccountId(this.project.getManager().getUserAccount().getId());
        return managerMember != null && managerMember.getId() == projectMemberId;
    }

}
