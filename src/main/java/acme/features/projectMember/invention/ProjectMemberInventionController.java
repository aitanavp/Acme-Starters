
package acme.features.projectMember.invention;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.inventions.Invention;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberInventionController extends AbstractController<ProjectMember, Invention> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberInventionListService.class);
		super.addBasicCommand("show", ProjectMemberInventionShowService.class);
		//super.addBasicCommand("create", ProjectMemberInventionCreateService.class);
		//super.addBasicCommand("update", ProjectMemberInventionUpdateService.class);
		//super.addBasicCommand("delete", ProjectMemberInventionDeleteService.class);

		//super.addCustomCommand("publish", "update", ProjectMemberInventionPublishService.class);
	}

}
