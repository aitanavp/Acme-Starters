
package acme.features.administrator.advertisement;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Administrator;
import acme.client.controllers.AbstractController;
import acme.entities.advertisements.Advertisement;

@Controller
public class AdministratorAdvertisementController extends AbstractController<Administrator, Advertisement> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AdministratorAdvertisementListService.class);
		super.addBasicCommand("show", AdministratorAdvertisementShowService.class);
		super.addBasicCommand("create", AdministratorAdvertisementCreateService.class);
		super.addBasicCommand("update", AdministratorAdvertisementUpdateService.class);
		super.addBasicCommand("delete", AdministratorAdvertisementDeleteService.class);
	}
}
