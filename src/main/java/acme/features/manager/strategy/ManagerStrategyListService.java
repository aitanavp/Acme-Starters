
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

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerStrategyRepository	repository;

	@Autowired
	private StrategyRepository			strategyRepository;

	private Collection<Strategy>		strategies;

	private Project						project;

	private Map<Integer, Double>		expectedPercentages;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int projectId;

		if (this.project == null) {
			projectId = super.getRequest().getData("projectId", int.class);
			this.project = this.repository.findProjectById(projectId);
		}

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
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		for (Strategy strategy : this.strategies) {
			Tuple tuple = super.unbindObject(strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "draftMode");
			tuple.put("expectedPercentage", this.expectedPercentages.get(strategy.getId()));
		}
	}

}
