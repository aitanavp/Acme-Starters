package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

    @Autowired
    private ManagerProjectRepository repository;

    private Project project;

    @Override
    public void load() {
        int id = super.getRequest().getData("id", int.class);
        this.project = this.repository.findProjectById(id);
    }

    @Override
    public void authorise() {
        boolean status = this.project != null && this.project.getManager().isPrincipal() && Boolean.TRUE.equals(this.project.getDraftMode());
        super.setAuthorised(status);
    }

    @Override
    public void bind() {
        super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
    }

    @Override
    public void validate() {
        super.validateObject(this.project);
    }

    @Override
    public void execute() {
        this.repository.save(this.project);
    }

    @Override
    public void unbind() {
        super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "effort");
    }

}
