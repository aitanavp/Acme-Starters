
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

	@Autowired
	private ProjectMemberAuditReportRepository	repository;
	private Collection<AuditReport>				auditReports;
	private Project								project;


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		ProjectMember projectMember;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		try {
			projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		} catch (final Throwable e) {
			projectMember = null;
		}
		status = this.project != null && projectMember != null && this.repository.isProjectMemberInProject(this.project.getId(), projectMember.getId());
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
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
