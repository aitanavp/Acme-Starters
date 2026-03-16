/*
 * FundraiserTacticDeleteService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.TacticKind;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticDeleteService extends AbstractService<Fundraiser, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Tactic tactic;

		id = super.getRequest().getData("id", int.class);
		tactic = this.repository.findTacticById(id);

		status = tactic != null && tactic.getStrategy().getDraftMode() && tactic.getStrategy().getFundraiser().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "description", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.delete(this.tactic);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(TacticKind.class, this.tactic.getKind());
		tuple = super.unbindObject(this.tactic, "name", "description", "expectedPercentage", "kind");
		tuple.put("kind", choices.getSelected().getKey());
		tuple.put("TacticKind", choices);

		super.unbindGlobal("strategyId", this.tactic.getStrategy().getId());
		super.unbindGlobal("draftMode", this.tactic.getStrategy().getDraftMode());
	}

}
