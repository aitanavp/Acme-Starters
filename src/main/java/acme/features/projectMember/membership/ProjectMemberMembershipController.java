package acme.features.projectMember.membership;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.ProjectMembership;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberMembershipController extends AbstractController<ProjectMember, ProjectMembership> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberMembershipListService.class);
		super.addBasicCommand("create", ProjectMemberMembershipAddService.class);
		super.addBasicCommand("delete", ProjectMemberMembershipDeleteService.class);
	}

}