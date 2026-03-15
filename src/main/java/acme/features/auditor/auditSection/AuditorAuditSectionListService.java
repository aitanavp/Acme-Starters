
package acme.features.auditor.auditSection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.entities.auditReports.AuditSection;
import acme.entities.auditors.Auditor;

@Service
public class AuditorAuditSectionListService extends AbstractService<Auditor, AuditSection> {
	// Internal state

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private List<AuditSection>				auditSections;

	private AuditReport						auditReport;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditReport = this.repository.findAuditReportById(auditReportId);

		status = this.auditReport != null && this.auditReport.getAuditor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int auditReportId;

		if (this.auditReport == null) {
			auditReportId = super.getRequest().getData("auditReportId", int.class);
			this.auditReport = this.repository.findAuditReportById(auditReportId);
		}

		this.auditSections = this.repository.findAuditSectionsByAuditReportId(this.auditReport.getId());
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");
		super.unbindGlobal("auditReportId", this.auditReport.getId());
		super.unbindGlobal("draftMode", this.auditReport.getDraftMode());
	}

}
