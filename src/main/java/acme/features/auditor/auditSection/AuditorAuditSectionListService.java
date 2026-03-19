
package acme.features.auditor.auditSection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.entities.auditReports.AuditSection;
import acme.realms.Auditor;

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
		for (AuditSection auditSection : this.auditSections) {
			Tuple tuple;
			String code;

			tuple = super.unbindObject(auditSection, "name", "notes", "hours", "kind");
			code = String.format("auditor.auditSection.kind.%s", auditSection.getKind());
			tuple.put("kind", MessageHelper.getMessage(code));
		}
		super.unbindGlobal("auditReportId", this.auditReport.getId());
		super.unbindGlobal("draftMode", this.auditReport.getDraftMode());
	}

}
