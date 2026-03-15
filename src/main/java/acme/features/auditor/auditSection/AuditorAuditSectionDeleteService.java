
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.SectionKind;
import acme.entities.auditReports.AuditSection;
import acme.entities.auditors.Auditor;

@Service
public class AuditorAuditSectionDeleteService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditSectionRepository	repository;

	private AuditSection					auditSection;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		AuditSection auditSection;

		id = super.getRequest().getData("id", int.class);
		auditSection = this.repository.findAuditSectionById(id);

		status = auditSection != null && auditSection.getAuditReport().getDraftMode() && auditSection.getAuditReport().getAuditor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditSection = this.repository.findAuditSectionById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.delete(this.auditSection);
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
