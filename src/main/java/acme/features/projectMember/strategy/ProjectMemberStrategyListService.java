package acme.features.projectMember.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;
import acme.realms.Fundraiser;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberStrategyListService extends AbstractService<ProjectMember, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberStrategyRepository	repository;

	@Autowired
	private StrategyRepository				strategyRepository;

	private Collection<Strategy>			strategies;

	private Collection<Strategy>			candidates;

	private Project							project;

	private Map<Integer, Double>			expectedPercentages;

	// AbstractService interface ---------------------------------------------

	@Override
	public void authorise() {
		boolean status;
		int projectId;
		ProjectMember projectMember;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);

		try {
			projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		} catch (final Throwable e) {
			projectMember = null;
		}

		status = this.project != null && projectMember != null
			&& this.repository.isProjectMemberInProject(this.project.getId(), projectMember.getId());

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Fundraiser fundraiser;
 
		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
 
		this.strategies = this.repository.findStrategiesByProjectId(this.project.getId());
 
		this.expectedPercentages = new HashMap<>();
		for (Strategy strategy : this.strategies) {
			Double computed = this.strategyRepository.computeExpectedPercentage(strategy.getId());
			if (computed == null)
				this.expectedPercentages.put(strategy.getId(), 0.0);
			else
				this.expectedPercentages.put(strategy.getId(), BigDecimal.valueOf(computed).setScale(2, RoundingMode.HALF_UP).doubleValue());
		}
 
		try {
			fundraiser = (Fundraiser) super.getRequest().getPrincipal().getRealmOfType(Fundraiser.class);
		} catch (final Throwable e) {
			fundraiser = null;
		}
 
		if (fundraiser != null)
			this.candidates = this.repository.findAvailableStrategiesByFundraiserId(fundraiser.getId(), this.project.getId());
	}

	@Override
	public void unbind() {
		for (Strategy strategy : this.strategies) {
			Tuple tuple = super.unbindObject(strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "draftMode");
			tuple.put("expectedPercentage", this.expectedPercentages.get(strategy.getId()));
		}
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		super.unbindGlobal("showCreate", this.candidates != null && !this.candidates.isEmpty());
	}

}