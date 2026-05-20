
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

	@Autowired
	private ManagerAuditReportRepository	repository;
	private Collection<AuditReport>			auditReports;
	private Project							project;


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);
		status = project != null && project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		if (this.project == null)
			return;
		this.auditReports = this.repository.findAuditReportsByProjectId(this.project.getId());
	}

	@Override
	public void unbind() {
		if (this.auditReports == null)
			return;
		super.unbindObjects(this.auditReports, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours", "draftMode");
	}
}
