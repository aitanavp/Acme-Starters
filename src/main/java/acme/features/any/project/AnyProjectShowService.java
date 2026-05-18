
package acme.features.any.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;

@Service
public class AnyProjectShowService extends AbstractService<Any, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectRepository	repository;

	private Project					project;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Project project = this.repository.findProjectById(id);
		boolean status = project != null && !project.getDraftMode();
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "effort");
	}

}
