
package acme.features.projectMember.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberStrategyListService extends AbstractService<ProjectMember, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberStrategyRepository	repository;

	private Collection<Strategy>			strategies;

	private Project							project;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int projectId;

		if (this.project == null) {
			projectId = super.getRequest().getData("projectId", int.class);
			this.project = this.repository.findProjectById(projectId);
		}

		this.strategies = this.repository.findStrategiesByProjectId(this.project.getId());
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage", "draftMode");
	}

}
