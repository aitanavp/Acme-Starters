
package acme.features.auditor.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.entities.projects.Project;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportShowService extends AbstractService<Auditor, AuditReport> {
	// Internal state

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		status = this.auditReport != null && this.auditReport.getAuditor().isPrincipal();
		super.setAuthorised(status);

	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		Collection<Project> projects = this.repository.findPublishedProjects();
		choices = SelectChoices.from(projects, "title", this.auditReport.getProject());

		double months = this.auditReport.getMonthsActive();
		int hours = this.auditReport.getHours();
		tuple = super.unbindObject(this.auditReport, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("allHours", hours);
		tuple.put("project", choices);
	}
}
