
package acme.features.auditor.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportCreateService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Auditor auditor;

		auditor = (Auditor) super.getRequest().getPrincipal().getActiveRealm();

		this.auditReport = super.newObject(AuditReport.class);
		this.auditReport.setDraftMode(true);
		this.auditReport.setAuditor(auditor);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		Auditor auditor = (Auditor) super.getRequest().getPrincipal().getActiveRealm();
		this.auditReport.setAuditor(auditor);
		this.auditReport.setDraftMode(true);

	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours");
		super.unbindGlobal("draftMode", this.auditReport.getDraftMode());
	}
}
