
package acme.features.manager.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;
import acme.realms.Manager;

@Service
public class ManagerStrategyShowService extends AbstractService<Manager, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerStrategyRepository	repository;

	private Strategy					strategy;
	private Double						expectedPercentage;

	// AbstractService interface ---------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Manager principal;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
		principal = (Manager) super.getRequest().getPrincipal().getRealmOfType(Manager.class);
		status = this.strategy != null && this.strategy.getProject() != null && principal != null && this.strategy.getProject().getManager().getId() == principal.getId();
		super.setAuthorised(status);

	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
		Double computed = this.strategyRepository.computeExpectedPercentage(this.strategy.getId());
		if (computed == null)
			this.expectedPercentage = 0.0;
		else
			this.expectedPercentage = java.math.BigDecimal.valueOf(computed).setScale(2, java.math.RoundingMode.HALF_UP).doubleValue();
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "draftMode");
		super.unbindGlobal("expectedPercentage", this.expectedPercentage);
		super.unbindGlobal("fundraiserId", this.strategy.getFundraiser().getId());
	}


	@Autowired
	private StrategyRepository strategyRepository;
}
