package acme.features.projectMember.strategy;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberStrategyCreateService extends AbstractService<ProjectMember, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberStrategyRepository	repository;

	private Project							project;

	private Strategy						strategy;

	private Collection<Strategy>			availableStrategies;

	// AbstractService interface ----------------------------------------------

	@Override
	public void authorise() {
	    boolean status;
	    int projectId;
	    ProjectMember projectMember;
	    Fundraiser fundraiser;

	    projectId = super.getRequest().getData("projectId", int.class);
	    this.project = this.repository.findProjectById(projectId);

	    try {
	        projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
	    } catch (final Throwable e) {
	        projectMember = null;
	    }

	    try {
	        fundraiser = (Fundraiser) super.getRequest().getPrincipal().getRealmOfType(Fundraiser.class);
	    } catch (final Throwable e) {
	        fundraiser = null;
	    }

	    status = this.project != null && this.project.getDraftMode()
	        && projectMember != null && fundraiser != null
	        && this.repository.isProjectMemberInProject(this.project.getId(), projectMember.getId());

	    super.setAuthorised(status);
	}

	@Override
	public void load() {
		Fundraiser fundraiser;

		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));

		fundraiser = (Fundraiser) super.getRequest().getPrincipal().getRealmOfType(Fundraiser.class);

		this.availableStrategies = this.repository.findAvailableStrategiesByFundraiserId(fundraiser.getId(), this.project.getId());

		this.strategy = null;
	}

	@Override
	public void bind() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.strategy = strategyId == 0 ? null : this.repository.findStrategyById(strategyId);
	}

	@Override
	public void validate() {
		Fundraiser fundraiser;

		fundraiser = (Fundraiser) super.getRequest().getPrincipal().getRealmOfType(Fundraiser.class);

		super.state(this.strategy != null, "strategyId", "project-member.strategy.form.error.required");
		if (this.strategy != null) {
			boolean isOwnStrategy;
			boolean isUnassigned;

			isOwnStrategy = this.strategy.getFundraiser() != null
				&& this.strategy.getFundraiser().getId() == fundraiser.getId();
			isUnassigned = this.strategy.getProject() == null;

			super.state(isOwnStrategy, "strategyId", "project-member.strategy.form.error.owner");
			super.state(isUnassigned, "strategyId", "project-member.strategy.form.error.assigned");
		}
	}

	@Override
	public void execute() {
		this.strategy.setProject(this.project);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		choices.add("0", "----", this.strategy == null);
		for (Strategy availableStrategy : this.availableStrategies) {
			String key;
			String label;

			key = String.valueOf(availableStrategy.getId());
			label = String.format("%s - %s", availableStrategy.getTicker(), availableStrategy.getName());
			choices.add(key, label, availableStrategy.equals(this.strategy));
		}
		if (this.strategy != null && !this.availableStrategies.contains(this.strategy))
			choices.add(
				String.valueOf(this.strategy.getId()),
				String.format("%s - %s", this.strategy.getTicker(), this.strategy.getName()),
				true
			);

		tuple = new Tuple();
		tuple.put("strategyId", choices.getSelected().getKey());
		tuple.put("strategyChoices", choices);

		super.getResponse().addData(tuple);

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}
}