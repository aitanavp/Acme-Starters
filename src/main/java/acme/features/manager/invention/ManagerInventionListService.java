
package acme.features.manager.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerInventionListService extends AbstractService<Manager, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerInventionRepository	repository;

	private Collection<Invention>		inventions;

	private Project						project;

	// AbstractService interface ---------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);
		status = project != null && project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		if (this.project == null)
			return;
		this.inventions = this.repository.findInventionsByProjectId(this.project.getId());
	}

	@Override
	public void unbind() {
		if (this.inventions == null)
			return;
		super.unbindObjects(this.inventions, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost", "draftMode");
	}

}
