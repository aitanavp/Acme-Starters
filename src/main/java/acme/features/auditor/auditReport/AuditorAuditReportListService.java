
package acme.features.auditor.auditReport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportListService extends AbstractService<Auditor, AuditReport> {
	// Internal state

	@Autowired
	private AuditorAuditReportRepository	repository;

	private List<AuditReport>				auditReports;

	// AbstractService interface


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.auditReports = this.repository.findAllAuditReportsByAuditorId(id);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours", "draftMode");
	}

}
