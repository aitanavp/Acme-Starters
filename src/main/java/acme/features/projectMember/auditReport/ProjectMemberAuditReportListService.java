
package acme.features.projectMember.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditReports.AuditReport;
import acme.entities.projects.Project;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberAuditReportListService extends AbstractService<ProjectMember, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberAuditReportRepository	repository;

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
