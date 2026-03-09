
package acme.features.any.inventor;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.inventors.Inventor;

@Controller
public class AnyInventorController extends AbstractController<Any, Inventor> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("show", AnyInventorShowService.class);
	}
}
