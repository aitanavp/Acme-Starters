/*
 * FundraiserStrategyPublishService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.strategy;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.features.fundraiser.tactic.FundraiserTacticRepository;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyPublishService extends AbstractService<Fundraiser, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	@Autowired
	private FundraiserTacticRepository		tacticReposiroty;

	private Strategy						strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Strategy strategy;

		id = super.getRequest().getData("id", int.class);
		strategy = this.repository.findStrategyById(id);

		status = strategy != null && strategy.getDraftMode() && strategy.getFundraiser().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);

		boolean hasTactics = this.tacticReposiroty.countByStrategyId(this.strategy.getId()) > 0;
		super.state(hasTactics, "*", "fundraiser.strategy.publish.error.no-tactics");

		Date start = this.strategy.getStartMoment();
		Date end = this.strategy.getEndMoment();
		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "fundraiser.strategy.publish.error.end-after-start");

		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "fundraiser.strategy.publish.error.start-future");

		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "fundraiser.strategy.publish.error.end-future");
	}

	@Override
	public void execute() {
		this.strategy.setDraftMode(false);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage", "draftMode");
		super.unbindGlobal("fundraiserId", this.strategy.getFundraiser().getId());
		super.unbindGlobal("draftMode", this.strategy.getDraftMode());
	}

}
