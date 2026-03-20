
package acme.features.any.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.helpers.MessageHelper;
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
		int id = super.getRequest().getData("id", int.class);
		Tactic tactic = this.repository.findTacticById(id);

		boolean status = tactic != null && tactic.getStrategy() != null && !tactic.getStrategy().getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		String code;

		tuple = super.unbindObject(this.tactic, "name", "description", "expectedPercentage", "kind");
		code = String.format("fundraiser.tactic.kind.%s", this.tactic.getKind());
		tuple.put("kind", MessageHelper.getMessage(code));

	}
}
