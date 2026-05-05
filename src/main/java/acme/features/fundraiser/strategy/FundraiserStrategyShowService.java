
package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyShowService extends AbstractService<Fundraiser, Strategy> {
	// Internal state

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy			strategy;
	private Double expectedPercentage;

	@Autowired
	private StrategyRepository strategyRepository;

	// AbstractService interface

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
		Double computed = this.strategyRepository.computeExpectedPercentage(this.strategy.getId());
		if (computed == null) {
			this.expectedPercentage = 0.0;
		} else {
			this.expectedPercentage = java.math.BigDecimal.valueOf(computed).setScale(2, java.math.RoundingMode.HALF_UP).doubleValue();
		}
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "draftMode");
		super.unbindGlobal("expectedPercentage", this.expectedPercentage);
		super.unbindGlobal("fundraiserId", this.strategy.getFundraiser().getId());
	}
}
