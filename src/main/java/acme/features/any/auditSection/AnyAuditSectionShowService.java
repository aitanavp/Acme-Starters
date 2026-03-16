
package acme.features.any.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditSection;

@Service
public class AnyAuditSectionShowService extends AbstractService<Any, AuditSection> {

	// Internal state

	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditSection				auditSection;

	// AbstractService interface


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		AuditSection auditSection = this.repository.findAuditSectionById(id);

		boolean status = auditSection != null && auditSection.getAuditReport() != null && !auditSection.getAuditReport().getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditSection = this.repository.findAuditSectionById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
	}
}
