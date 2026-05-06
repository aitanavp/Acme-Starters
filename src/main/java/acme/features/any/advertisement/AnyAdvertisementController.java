
package acme.features.any.advertisement;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.advertisements.Advertisement;

@Controller
public class AnyAdvertisementController extends AbstractController<Any, Advertisement> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AnyAdvertisementListService.class);
		super.addBasicCommand("show", AnyAdvertisementShowService.class);
	}
}
