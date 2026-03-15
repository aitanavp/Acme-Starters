
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.SectionKind;
import acme.entities.auditReports.AuditReport;
import acme.entities.auditReports.AuditSection;
import acme.entities.auditors.Auditor;

@Service
public class AuditorAuditSectionCreateService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;

	private AuditReport						auditReport;

	// AbstractService interface -----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int auditReportId;

		auditReportId = super.getRequest().getData("auditReportId", int.class);
		this.auditReport = this.repository.findAuditReportById(auditReportId);

		status = this.auditReport != null && this.auditReport.getDraftMode() && this.auditReport.getAuditor().isPrincipal();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int auditReportId;

		if (this.auditReport == null) {
			auditReportId = super.getRequest().getData("auditReportId", int.class);
			this.auditReport = this.repository.findAuditReportById(auditReportId);
		}

		this.auditSection = super.newObject(AuditSection.class);
		this.auditSection.setAuditReport(this.auditReport);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());
		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("kind", choices.getSelected().getKey());
		tuple.put("SectionKind", choices);

		super.unbindGlobal("auditReportId", this.auditSection.getAuditReport().getId());
		super.unbindGlobal("draftMode", this.auditSection.getAuditReport().getDraftMode());
	}
}
