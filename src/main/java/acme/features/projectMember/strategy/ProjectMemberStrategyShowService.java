
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
		Strategy strategy;

		id = super.getRequest().getData("id", int.class);
		strategy = this.repository.findStrategyById(id);

		status = strategy != null && strategy.getFundraiser().isPrincipal();
		super.getResponse().setAuthorised(status);
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
