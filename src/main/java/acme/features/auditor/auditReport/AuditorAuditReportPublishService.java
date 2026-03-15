
package acme.features.auditor.auditReport;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.features.auditor.auditSection.AuditorAuditSectionRepository;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportPublishService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	@Autowired
	private AuditorAuditSectionRepository	auditSectionRepository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		AuditReport auditReport;

		id = super.getRequest().getData("id", int.class);
		auditReport = this.repository.findAuditReportById(id);

		status = auditReport != null && auditReport.getDraftMode() && auditReport.getAuditor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);
		boolean hasAuditSections = this.auditSectionRepository.countByAuditReportId(this.auditReport.getId()) > 0;
		super.state(hasAuditSections, "*", "auditor.auditReport.publish.error.no-auditSections");

		Date start = this.auditReport.getStartMoment();
		Date end = this.auditReport.getEndMoment();
		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "auditor.auditReport.publish.error.end-after-start");

		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "auditor.auditReport.publish.error.start-future");

		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "auditor.auditReport.publish.error.end-future");
	}

	@Override
	public void execute() {
		this.auditReport.setDraftMode(false);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours", "draftMode");
		super.unbindGlobal("auditorId", this.auditReport.getAuditor().getId());
	}

}
