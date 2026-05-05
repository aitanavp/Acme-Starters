
package acme.features.any.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;

@Service
public class AnyStrategyShowService extends AbstractService<Any, Strategy> {
	// Internal state

	@Autowired
	private AnyStrategyRepository	repository;

	private Strategy				strategy;
	private Double expectedPercentage;

	// AbstractService interface


	@Override
	public void authorise() {

		int id = super.getRequest().getData("id", int.class);
		Strategy strategy = this.repository.findStrategyById(id);
		boolean status = strategy != null && !strategy.getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
		Double computed = this.getStrategyRepository().computeExpectedPercentage(this.strategy.getId());
		if (computed == null) {
			this.expectedPercentage = 0.0;
		} else {
			this.expectedPercentage = java.math.BigDecimal.valueOf(computed).setScale(2, java.math.RoundingMode.HALF_UP).doubleValue();
		}
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive");
		super.unbindGlobal("expectedPercentage", this.expectedPercentage);
		super.unbindGlobal("fundraiserId", this.strategy.getFundraiser().getId());
	}

	// Helper to lazily get StrategyRepository from Spring context to avoid adding a
	// hard dependency on it in generated repositories.
	@Autowired
	private StrategyRepository strategyRepository;

	private StrategyRepository getStrategyRepository() {
		return this.strategyRepository;
	}
}
