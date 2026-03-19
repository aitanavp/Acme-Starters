
package acme.features.fundraiser.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyListService extends AbstractService<Fundraiser, Strategy> {
	// Internal state

	@Autowired
	private FundraiserStrategyRepository	repository;

	private List<Strategy>					strategies;

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
	}

	@Override
	public void unbind() {
		for (Strategy strategy : this.strategies) {
			Tuple tuple;
			String code;

			tuple = super.unbindObject(strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage", "draftMode");
			code = strategy.getDraftMode() ? "fundraiser.strategy.list.draftMode.true" : "fundraiser.strategy.list.draftMode.false";
			tuple.put("draftMode", MessageHelper.getMessage(code));
		}
	}

}
