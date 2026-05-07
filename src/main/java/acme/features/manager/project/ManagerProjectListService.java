
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectListService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository	repository;

	private Collection<Project>			projects;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		Manager manager;
		int id;

		manager = (Manager) super.getRequest().getPrincipal().getRealmOfType(Manager.class);
		id = manager.getId();
		this.projects = this.repository.findProjectsByManagerId(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "effort");
	}

}
