
package acme.features.auditor.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.entities.projects.Project;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportLinkProjectService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && !this.auditReport.getDraftMode() && this.auditReport.getAuditor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "project");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);
		{
			boolean correctNumberOfAuditSections;
			correctNumberOfAuditSections = this.repository.getNumberOfAuditSectionsByAuditReportId(this.auditReport.getId()) >= 1;
			super.state(correctNumberOfAuditSections, "*", "acme.validation.numberOfAuditSections.message");
		}
		{
			boolean isBefore;
			isBefore = this.auditReport.getStartMoment().before(this.auditReport.getEndMoment());
			super.state(isBefore, "*", "acme.validation.correctDates.message");
		}
		{
			boolean startFuture;
			startFuture = MomentHelper.isFuture(this.auditReport.getStartMoment());

			super.state(startFuture, "startMoment", "acme.validation.invention.future-interval.message");
		}
		{
			boolean duplicated;

			duplicated = this.repository.tickerExists(this.auditReport.getTicker(), this.auditReport.getId());

			super.state(!duplicated, "ticker", "acme.validation.duplicated-ticker.message");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		Collection<Project> projects = this.repository.findPublishedProjects();
		choices = SelectChoices.from(projects, "title", this.auditReport.getProject());
		double months = this.auditReport.getMonthsActive();
		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("project", choices);
	}

}
