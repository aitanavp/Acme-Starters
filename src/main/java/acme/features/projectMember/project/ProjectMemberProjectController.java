
package acme.features.projectMember.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberProjectController extends AbstractController<ProjectMember, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberProjectListService.class);
		super.addBasicCommand("show", ProjectMemberProjectShowService.class);
		//super.addBasicCommand("create", ProjectMemberProjectCreateService.class);
		//super.addBasicCommand("update", ProjectMemberProjectUpdateService.class);
		//super.addBasicCommand("delete", ProjectMemberProjectDeleteService.class);

		//super.addCustomCommand("publish", "update", InventorInventionPublishService.class);
	}

}
