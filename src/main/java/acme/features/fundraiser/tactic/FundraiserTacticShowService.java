
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.datatypes.TacticKind;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticShowService extends AbstractService<Fundraiser, Tactic> {
	// Internal state

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int id;
		Tactic tactic;

		id = super.getRequest().getData("id", int.class);
		tactic = this.repository.findTacticById(id);

		status = tactic != null && tactic.getStrategy().getFundraiser().isPrincipal();
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
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		choices.add("0", "----", this.tactic.getKind() == null);
		for (TacticKind kind : TacticKind.values()) {
			String key;
			String label;

			key = kind.toString();
			label = MessageHelper.getMessage(String.format("fundraiser.tactic.kind.%s", key));
			choices.add(key, label, kind.equals(this.tactic.getKind()));
		}
		tuple = super.unbindObject(this.tactic, "name", "description", "expectedPercentage", "kind");
		tuple.put("kind", choices.getSelected().getKey());
		tuple.put("TacticKind", choices);

		super.unbindGlobal("strategyId", this.tactic.getStrategy().getId());
		super.unbindGlobal("draftMode", this.tactic.getStrategy().getDraftMode());
	}
}
