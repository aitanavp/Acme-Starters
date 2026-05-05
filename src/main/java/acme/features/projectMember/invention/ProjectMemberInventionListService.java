
package acme.features.projectMember.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberInventionListService extends AbstractService<ProjectMember, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberInventionRepository	repository;

	private Collection<Invention>				inventions;

	private Project								project;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int projectId;

		if (this.project == null) {
			projectId = super.getRequest().getData("projectId", int.class);
			this.project = this.repository.findProjectById(projectId);
		}

		this.inventions = this.repository.findInventionsByProjectId(this.project.getId());
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost", "draftMode");
	}

}
