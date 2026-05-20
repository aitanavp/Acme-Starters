
package acme.features.manager.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;
import acme.realms.Manager;

@Service
public class ManagerStrategyListService extends AbstractService<Manager, Strategy> {

	@Autowired
	private ManagerStrategyRepository	repository;
	@Autowired
	private StrategyRepository			strategyRepository;
	private Collection<Strategy>		strategies;
	private Project						project;
	private Map<Integer, Double>		expectedPercentages;


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
		this.strategies = this.repository.findStrategiesByProjectId(this.project.getId());
		this.expectedPercentages = new HashMap<>();
		for (Strategy strategy : this.strategies) {
			Double computed = this.strategyRepository.computeExpectedPercentage(strategy.getId());
			if (computed == null)
				this.expectedPercentages.put(strategy.getId(), 0.0);
			else {
				Double rounded = BigDecimal.valueOf(computed).setScale(2, RoundingMode.HALF_UP).doubleValue();
				this.expectedPercentages.put(strategy.getId(), rounded);
			}
		}
	}

	@Override
	public void unbind() {
		if (this.strategies == null)
			return;
		for (Strategy strategy : this.strategies) {
			Tuple tuple = super.unbindObject(strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "draftMode");
			tuple.put("expectedPercentage", this.expectedPercentages.get(strategy.getId()));
		}
	}
}
