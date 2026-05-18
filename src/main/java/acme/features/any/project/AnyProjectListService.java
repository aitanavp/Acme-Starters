
package acme.features.any.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;

@Service
public class AnyProjectListService extends AbstractService<Any, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectRepository	repository;

	private Collection<Project>		projects;

	// AbstractService interface ---------------------------------------------


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "effort");
	}

	@Override
	public void load() {
		this.projects = this.repository.findAllPublishedProjects();
	}
}
