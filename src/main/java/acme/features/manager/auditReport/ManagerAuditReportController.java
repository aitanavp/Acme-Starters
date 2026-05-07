
package acme.features.manager.auditReport;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.auditReports.AuditReport;
import acme.realms.Manager;

@Controller
public class ManagerAuditReportController extends AbstractController<Manager, AuditReport> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ManagerAuditReportListService.class);
		super.addBasicCommand("show", ManagerAuditReportShowService.class);
		//super.addBasicCommand("create", ManagerInventionCreateService.class);
		//super.addBasicCommand("update", ManagerInventionUpdateService.class);
		//super.addBasicCommand("delete", ManagerInventionDeleteService.class);

		//super.addCustomCommand("publish", "update", ManagerInventionPublishService.class);
	}

}
