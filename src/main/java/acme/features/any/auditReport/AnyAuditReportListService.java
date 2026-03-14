
package acme.features.any.auditReport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;

@Service
public class AnyAuditReportListService extends AbstractService<Any, AuditReport> {

	// Internal state

	@Autowired
	private AnyAuditReportRepository	repository;

	private List<AuditReport>			auditReports;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours");
	}

	@Override
	public void load() {
		this.auditReports = this.repository.findAllPublishedAuditReports();
	}

}
