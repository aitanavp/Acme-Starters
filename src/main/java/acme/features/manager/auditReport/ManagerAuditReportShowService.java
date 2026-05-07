
package acme.features.manager.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.realms.Manager;

@Service
public class ManagerAuditReportShowService extends AbstractService<Manager, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface ---------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Manager principal;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
		principal = (Manager) super.getRequest().getPrincipal().getRealmOfType(Manager.class);
		status = this.auditReport != null && this.auditReport.getProject() != null && principal != null && this.repository.isProjectMemberInProject(this.auditReport.getProject().getId(), principal.getId());
		super.setAuthorised(status);

	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours", "draftMode");
		super.unbindGlobal("auditorId", this.auditReport.getAuditor().getId());
	}

}
