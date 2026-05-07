
package acme.features.manager.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Manager;

@Controller
public class ManagerSponsorshipController extends AbstractController<Manager, Sponsorship> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ManagerSponsorshipListService.class);
		super.addBasicCommand("show", ManagerSponsorshipShowService.class);
		//super.addBasicCommand("create", ManagerInventionCreateService.class);
		//super.addBasicCommand("update", ManagerInventionUpdateService.class);
		//super.addBasicCommand("delete", ManagerInventionDeleteService.class);

		//super.addCustomCommand("publish", "update", ManagerInventionPublishService.class);
	}

}
