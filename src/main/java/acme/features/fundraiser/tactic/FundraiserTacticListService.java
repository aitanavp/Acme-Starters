
package acme.features.fundraiser.tactic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticListService extends AbstractService<Fundraiser, Tactic> {
	// Internal state

	@Autowired
	private FundraiserTacticRepository	repository;

	private List<Tactic>				tactics;

	private Strategy				strategy;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.strategy = this.repository.findStrategyById(strategyId);

		status = this.strategy != null && this.strategy.getFundraiser().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int strategyId;

		if (this.strategy == null) {
			strategyId = super.getRequest().getData("strategyId", int.class);
			this.strategy = this.repository.findStrategyById(strategyId);
		}

		this.tactics = this.repository.findTacticsByStrategyId(this.strategy.getId());
	}

	@Override
	public void unbind() {
		for (Tactic tactic : this.tactics) {
			Tuple tuple;
			String code;

			tuple = super.unbindObject(tactic, "name", "description", "expectedPercentage", "kind");
			code = String.format("fundraiser.tactic.kind.%s", tactic.getKind());
			tuple.put("kind", MessageHelper.getMessage(code));
		}
		super.unbindGlobal("strategyId", this.strategy.getId());
		super.unbindGlobal("draftMode", this.strategy.getDraftMode());
	}

}
