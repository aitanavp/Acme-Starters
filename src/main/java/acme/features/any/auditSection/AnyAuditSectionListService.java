
package acme.features.any.auditSection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditSection;

@Service
public class AnyAuditSectionListService extends AbstractService<Any, AuditSection> {

	// Internal state

	@Autowired
	private AnyAuditSectionRepository	repository;

	private List<AuditSection>			auditSections;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditSections, "name", "notes", "hours", "kind");
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("auditReportId", int.class);
		this.auditSections = this.repository.findAllAuditSectionsByAuditReportId(id);
	}

}
