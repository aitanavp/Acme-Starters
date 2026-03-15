
package acme.features.any.tactic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.features.any.strategy.AnyStrategyRepository;

@Service
public class AnyTacticListService extends AbstractService<Any, Tactic> {
	// Internal state

	@Autowired
	private AnyTacticRepository		repository;

	@Autowired
	private AnyStrategyRepository	strategyRepository;

	private List<Tactic>			tactics;

	// AbstractService interface


	@Override
	public void authorise() {
		int strategyId = super.getRequest().getData("strategyId", int.class);
		Strategy strategy = this.strategyRepository.findStrategyById(strategyId);

		boolean status = strategy != null && !strategy.getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "description", "expectedPercentage", "kind");
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findPublishedTacticsByStrategyId(id);
	}
}
