
package acme.features.projectMember.auditReport;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.auditReports.AuditReport;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberAuditReportController extends AbstractController<ProjectMember, AuditReport> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberAuditReportListService.class);
		super.addBasicCommand("show", ProjectMemberAuditReportShowService.class);
		//super.addBasicCommand("create", ProjectMemberInventionCreateService.class);
		//super.addBasicCommand("update", ProjectMemberInventionUpdateService.class);
		//super.addBasicCommand("delete", ProjectMemberInventionDeleteService.class);

		//super.addCustomCommand("publish", "update", ProjectMemberInventionPublishService.class);
	}

}
