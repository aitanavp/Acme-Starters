
package acme.features.any.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;

@Service
public class AnyAuditReportShowService extends AbstractService<Any, AuditReport> {

	// Internal state

	@Autowired
	private AnyAuditReportRepository	repository;

	private AuditReport					auditReport;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "name", "description", "startMoment", "endMoment", "moreInfo");
		super.unbindGlobal("auditReportId", this.auditReport.getAuditor().getId());
	}
}
