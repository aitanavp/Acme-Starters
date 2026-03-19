/*
 * FundraiserTacticUpdateService.java
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
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.datatypes.TacticKind;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticUpdateService extends AbstractService<Fundraiser, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;

	// AbstractService interface ----------------------------------------------


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
		super.validateObject(this.tactic);

		if (this.tactic.getExpectedPercentage() != null) {
			Double otherSum = this.repository.sumExpectedPercentageByStrategyIdExcludingTactic(this.tactic.getStrategy().getId(), this.tactic.getId());
			if (otherSum == null)
				otherSum = 0.0;
			double newTotal = otherSum + this.tactic.getExpectedPercentage();
			super.state(newTotal <= 100.0, "expectedPercentage", "fundraiser.tactic.form.error.percentage-exceeded");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
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
