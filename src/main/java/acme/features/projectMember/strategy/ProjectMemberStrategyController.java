
package acme.features.projectMember.strategy;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.strategies.Strategy;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberStrategyController extends AbstractController<ProjectMember, Strategy> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberStrategyListService.class);
		super.addBasicCommand("show", ProjectMemberStrategyShowService.class);
		//super.addBasicCommand("create", ProjectMemberInventionCreateService.class);
		//super.addBasicCommand("update", ProjectMemberInventionUpdateService.class);
		//super.addBasicCommand("delete", ProjectMemberInventionDeleteService.class);

		//super.addCustomCommand("publish", "update", ProjectMemberInventionPublishService.class);
	}

}
