
package acme.features.manager.project;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.features.fundraiser.tactic.FundraiserTacticRepository;
import acme.features.inventor.part.InventorPartRepository;
import acme.features.spokesperson.milestone.SpokespersonMilestoneRepository;
import acme.realms.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository		repository;

	@Autowired
	private InventorPartRepository			partRepository;

	private Project							project;

	private Collection<Invention>			inventions;

	private Collection<Campaign>			campaigns;

	private Collection<Strategy>			strategies;

	@Autowired
	private FundraiserTacticRepository		tacticReposiroty;

	@Autowired
	private SpokespersonMilestoneRepository	milestoneRepository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		Project project;

		id = super.getRequest().getData("id", int.class);
		project = this.repository.findProjectById(id);

		status = project != null && project.getDraftMode() && project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
		if (this.project != null) {
			this.inventions = this.repository.findInventionsByProjectId(this.project.getId());
			this.campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
			this.strategies = this.repository.findStrategiesByProjectId(this.project.getId());
		}

	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
	}

	@Override
	public void validate() {
		if (this.project == null)
			return;

		super.validateObject(this.project);

		Date kickOff = this.project.getKickOffMoment();
		Date closeOut = this.project.getCloseOutMoment();

		if (kickOff != null)
			super.state(MomentHelper.isFuture(kickOff), "kickOffMoment", "manager.project.publish.error.kickoff-future");

		if (closeOut != null)
			super.state(MomentHelper.isFuture(closeOut), "closeOutMoment", "manager.project.publish.error.closeout-future");

		// Earliest startMoment among all components
		Date earliestStart = null;
		Date s1 = this.repository.findEarliestStrategyStart(this.project.getId());
		Date s2 = this.repository.findEarliestInventionStart(this.project.getId());
		Date s3 = this.repository.findEarliestCampaignStart(this.project.getId());
		for (Date d : new Date[] {
			s1, s2, s3
		})
			if (d != null && (earliestStart == null || d.before(earliestStart)))
				earliestStart = d;

		if (kickOff != null && earliestStart != null)
			super.state(!MomentHelper.isAfter(kickOff, earliestStart), "kickOffMoment", "manager.project.publish.error.kickoff-after-earliest-start");

		// Latest endMoment among all components
		Date latestEnd = null;
		Date e1 = this.repository.findLatestStrategyEnd(this.project.getId());
		Date e2 = this.repository.findLatestInventionEnd(this.project.getId());
		Date e3 = this.repository.findLatestCampaignEnd(this.project.getId());
		for (Date d : new Date[] {
			e1, e2, e3
		})
			if (d != null && (latestEnd == null || d.after(latestEnd)))
				latestEnd = d;
		if (closeOut != null && latestEnd != null)
			super.state(!MomentHelper.isBefore(closeOut, latestEnd), "closeOutMoment", "manager.project.publish.error.closeout-before-latest-end");

		// INVENTIONS
		boolean hasInventions = this.inventions != null && !this.inventions.isEmpty();
		super.state(hasInventions, "*", "manager.project.publish.error.no-inventions");

		if (hasInventions)
			for (Invention invention : this.inventions) {
				super.state(!invention.getDraftMode() || true, "*", "*");

				boolean hasParts = this.partRepository.countByInventionId(invention.getId()) > 0;
				super.state(hasParts, "*", "manager.project.publish.error.invention.no-parts");

				Date iStart = invention.getStartMoment();
				Date iEnd = invention.getEndMoment();
				if (iStart != null && iEnd != null)
					super.state(MomentHelper.isAfter(iEnd, iStart), "*", "manager.project.publish.error.invention.end-after-start");
				if (iStart != null)
					super.state(MomentHelper.isFuture(iStart), "*", "manager.project.publish.error.invention.start-future");
				if (iEnd != null)
					super.state(MomentHelper.isFuture(iEnd), "*", "manager.project.publish.error.invention.end-future");
			}

		// STRATEGIES
		for (Strategy strategy : this.strategies) {
			boolean hasTactics = this.tacticReposiroty.countByStrategyId(strategy.getId()) > 0;
			super.state(hasTactics, "*", "manager.project.strategy.publish.error.no-tactics");

			Date start = strategy.getStartMoment();
			Date end = strategy.getEndMoment();
			if (start != null && end != null)
				super.state(MomentHelper.isAfter(end, start), "*", "manager.project.strategy.publish.error.end-after-start");
			if (start != null)
				super.state(MomentHelper.isFuture(start), "*", "manager.project.strategy.publish.error.start-future");
			if (end != null)
				super.state(MomentHelper.isFuture(end), "*", "manager.project.strategy.publish.error.end-future");
		}

		// CAMPAIGNS
		for (Campaign campaign : this.campaigns) {
			boolean hasMilestones = this.milestoneRepository.countByCampaignId(campaign.getId()) > 0;
			super.state(hasMilestones, "*", "manager.project.campaign.publish.error.no-milestones");

			Date start = campaign.getStartMoment();
			Date end = campaign.getEndMoment();
			if (start != null && end != null)
				super.state(MomentHelper.isAfter(end, start), "*", "manager.project.campaign.publish.error.end-after-start");
			if (start != null)
				super.state(MomentHelper.isFuture(start), "*", "manager.project.campaign.publish.error.start-future");
			if (end != null)
				super.state(MomentHelper.isFuture(end), "*", "manager.project.campaign.publish.error.end-future");
		}
	}

	@Override
	public void execute() {
		if (this.inventions != null)
			for (Invention invention : this.inventions) {
				invention.setDraftMode(false);
				this.repository.save(invention);
			}
		if (this.strategies != null)
			for (Strategy strategy : this.strategies) {
				strategy.setDraftMode(false);
				this.repository.save(strategy);
			}

		if (this.campaigns != null)
			for (Campaign campaign : this.campaigns) {
				campaign.setDraftMode(false);
				this.repository.save(campaign);
			}

		this.project.setDraftMode(false);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "effort");
		super.unbindGlobal("managerId", this.project.getManager().getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}
