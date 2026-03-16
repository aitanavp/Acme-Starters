
package acme.features.auditor.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.entities.auditReports.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportDeleteService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
		status = this.auditReport != null && this.auditReport.getDraftMode() && this.auditReport.getAuditor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		this.auditReport.setAuditor(this.auditReport.getAuditor());
		this.auditReport.setDraftMode(true);
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		Collection<AuditSection> auditSections;
		auditSections = this.repository.findAuditSectionsByAuditReportId(this.auditReport.getId());
		this.repository.deleteAll(auditSections);
		this.repository.delete(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours", "draftMode");
		super.unbindGlobal("auditorId", this.auditReport.getAuditor().getId());
	}

}
