package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

    @Autowired
    private ManagerProjectRepository repository;

    private Project project;

    @Override
    public void load() {
        Manager manager = (Manager) super.getRequest().getPrincipal().getRealmOfType(Manager.class);
        this.project = super.newObject(Project.class);
        this.project.setDraftMode(true);
        this.project.setManager(manager);
    }

    @Override
    public void authorise() {
        Manager principal = (Manager) super.getRequest().getPrincipal().getRealmOfType(Manager.class);
        super.setAuthorised(principal != null);
    }

    @Override
    public void bind() {
        super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
        Manager manager = (Manager) super.getRequest().getPrincipal().getRealmOfType(Manager.class);
        this.project.setManager(manager);
        this.project.setDraftMode(true);
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
        super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "effort");
        super.unbindGlobal("draftMode", this.project.getDraftMode());
    }

}
