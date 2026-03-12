/*
 * FundraiserTacticCreateService.java
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
import acme.entities.fundraisers.Fundraiser;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Service
public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;

	private Strategy					strategy;

	// AbstractService interface -----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.strategy = this.repository.findStrategyById(strategyId);

		status = this.strategy != null && this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int strategyId;

		if (this.strategy == null) {
			strategyId = super.getRequest().getData("strategyId", int.class);
			this.strategy = this.repository.findStrategyById(strategyId);
		}

		this.tactic = super.newObject(Tactic.class);
		this.tactic.setStrategy(this.strategy);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "description", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
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
