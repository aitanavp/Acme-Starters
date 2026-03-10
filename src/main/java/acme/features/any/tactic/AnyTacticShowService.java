
package acme.features.any.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;

@Service
public class AnyTacticShowService extends AbstractService<Any, Tactic> {

	// Internal state

	@Autowired
	private AnyTacticRepository	repository;

	private Tactic				tactic;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, "name", "description", "expectedPercentage", "kind");
	}
}
