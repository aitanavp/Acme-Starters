
package acme.features.manager.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerAuditReportListService extends AbstractService<Manager, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerAuditReportRepository	repository;

	private Collection<AuditReport>				auditReports;

	private Project								project;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int projectId;

		if (this.project == null) {
			projectId = super.getRequest().getData("projectId", int.class);
			this.project = this.repository.findProjectById(projectId);
		}

		this.auditReports = this.repository.findAuditReportsByProjectId(this.project.getId());
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours", "draftMode");
	}

}
