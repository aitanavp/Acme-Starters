
package acme.features.any.tactic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;

@Service
public class AnyTacticListService extends AbstractService<Any, Tactic> {
	// Internal state

	@Autowired
	private AnyTacticRepository	repository;

	private List<Tactic>		tactics;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "description", "expectedPercentage", "kind");
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findAllTacticsByStrategyId(id);
	}
}
