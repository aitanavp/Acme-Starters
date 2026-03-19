
package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyShowService extends AbstractService<Fundraiser, Strategy> {
	// Internal state

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;

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
	}

	@Override
	public void unbind() {
		Tuple tuple;
		String code;
		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage", "draftMode");
		code = this.strategy.getDraftMode() ? "fundraiser.strategy.list.draftMode.true" : "fundraiser.strategy.list.draftMode.false";
		tuple.put("draftMode", MessageHelper.getMessage(code));
		super.unbindGlobal("fundraiserId", this.strategy.getFundraiser().getId());
	}
}
