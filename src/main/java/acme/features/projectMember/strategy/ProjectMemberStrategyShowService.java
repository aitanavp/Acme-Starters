
package acme.features.projectMember.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberStrategyShowService extends AbstractService<ProjectMember, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberStrategyRepository	repository;

	private Strategy							strategy;

	// AbstractService interface ---------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		ProjectMember principal;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
		principal = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);

		status = this.strategy != null && this.strategy.getProject() != null && principal != null
			&& this.repository.isProjectMemberInProject(this.strategy.getProject().getId(), principal.getId());
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage", "draftMode");
		super.unbindGlobal("fundraiserId", this.strategy.getFundraiser().getId());
	}

}
