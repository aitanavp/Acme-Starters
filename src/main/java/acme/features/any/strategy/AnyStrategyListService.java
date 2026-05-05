
package acme.features.any.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;

@Service
public class AnyStrategyListService extends AbstractService<Any, Strategy> {
	// Internal state

	@Autowired
	private AnyStrategyRepository	repository;

	@Autowired
	private StrategyRepository strategyRepository;

	private List<Strategy>			strategies;
	private Map<Integer, Double> expectedPercentages;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		this.strategies = this.repository.findAllPublishedStrategies();
		this.expectedPercentages = new HashMap<>();
		for (Strategy strategy : this.strategies) {
			Double computed = this.strategyRepository.computeExpectedPercentage(strategy.getId());
			if (computed == null) {
				this.expectedPercentages.put(strategy.getId(), 0.0);
			} else {
				Double rounded = BigDecimal.valueOf(computed).setScale(2, RoundingMode.HALF_UP).doubleValue();
				this.expectedPercentages.put(strategy.getId(), rounded);
			}
		}
	}

	@Override
	public void unbind() {
		for (Strategy strategy : this.strategies) {
			Tuple tuple = super.unbindObject(strategy, "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive");
			tuple.put("expectedPercentage", this.expectedPercentages.get(strategy.getId()));
		}
	}
}
