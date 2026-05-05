
package acme.features.projectMember.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.ProjectMember;

@Controller
public class ProjectMemberSponsorshipController extends AbstractController<ProjectMember, Sponsorship> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberSponsorshipListService.class);
		super.addBasicCommand("show", ProjectMemberSponsorshipShowService.class);
		//super.addBasicCommand("create", ProjectMemberInventionCreateService.class);
		//super.addBasicCommand("update", ProjectMemberInventionUpdateService.class);
		//super.addBasicCommand("delete", ProjectMemberInventionDeleteService.class);

		//super.addCustomCommand("publish", "update", ProjectMemberInventionPublishService.class);
	}

}
