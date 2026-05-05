
package acme.features.fundraiser.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyListService extends AbstractService<Fundraiser, Strategy> {
	// Internal state

	@Autowired
	private FundraiserStrategyRepository	repository;

	@Autowired
	private StrategyRepository strategyRepository;

	private List<Strategy>					strategies;

	private Map<Integer, Double> expectedPercentages;

	// AbstractService interface


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategies = this.repository.findAllStrategyByFundraiserId(id);
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
			Tuple tuple;
			String code;

			tuple = super.unbindObject(strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "draftMode");
			tuple.put("expectedPercentage", this.expectedPercentages.get(strategy.getId()));
			code = strategy.getDraftMode() ? "fundraiser.strategy.list.draftMode.true" : "fundraiser.strategy.list.draftMode.false";
			tuple.put("draftMode", MessageHelper.getMessage(code));
		}
	}

}
