
package acme.features.any.auditSection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.entities.auditReports.AuditSection;
import acme.features.any.auditReport.AnyAuditReportRepository;

@Service
public class AnyAuditSectionListService extends AbstractService<Any, AuditSection> {

	// Internal state

	@Autowired
	private AnyAuditSectionRepository	repository;

	@Autowired
	private AnyAuditReportRepository	auditReportRepository;

	private List<AuditSection>			auditSections;

	// AbstractService interface


	@Override
	public void authorise() {
		int auditReportId = super.getRequest().getData("auditReportId", int.class);
		AuditReport auditReport = this.auditReportRepository.findAuditReportById(auditReportId);

		boolean status = auditReport != null && !auditReport.getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void unbind() {
		for (AuditSection auditSection : this.auditSections) {
			Tuple tuple;
			String code;

			tuple = super.unbindObject(auditSection, "name", "notes", "hours", "kind");
			code = String.format("any.audit-section.kind.%s", auditSection.getKind());
			tuple.put("kind", MessageHelper.getMessage(code));
		}
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("auditReportId", int.class);
		this.auditSections = this.repository.findPublishedAuditSectionsByAuditReportId(id);
	}

}
