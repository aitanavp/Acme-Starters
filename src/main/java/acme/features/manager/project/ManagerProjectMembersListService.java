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
    private ProjectMemberMembershipRepository repository;

    private Collection<ProjectMembership> memberships;

    private Project project;

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
        ProjectMembership membership;
        
        if (this.project == null)
            this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));

        // ensure manager is member
        acme.realms.ProjectMember managerMember = this.repository.findProjectMemberByUserAccountId(this.project.getManager().getUserAccount().getId());
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

            tuple = new Tuple();
            java.util.List<String> buffer = new java.util.ArrayList<>(java.util.Arrays.asList("projectMember"));
            if (membership instanceof acme.client.components.basis.AbstractEntity) {
                if (!buffer.contains("id"))
                    buffer.add("id");
                if (!buffer.contains("version"))
                    buffer.add("version");
            }
            String[] inclusions = buffer.toArray(new String[0]);
            acme.internals.helpers.BinderHelper.unbind(membership, super.getResponse().getErrors(), super.getRequest().getData(), tuple, inclusions);
            tuple.put("projectMember", membership.getProjectMember().getUserAccount().getUsername());
            super.getResponse().addData(tuple);
        }
        super.unbindGlobal("projectId", this.project.getId());
        super.unbindGlobal("draftMode", this.project.getDraftMode());
        super.unbindGlobal("canManageMembers", this.project.getDraftMode() && this.project.getManager().isPrincipal());
    }

}
