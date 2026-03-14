
package acme.features.inventor.part;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;

import acme.client.controllers.AbstractController;
import acme.entities.inventions.Part;
import acme.entities.inventors.Inventor;

public class InventorPartController extends AbstractController<Inventor, Part> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", InventorPartListService.class);
		super.addBasicCommand("show", InventorPartShowService.class);
		super.addBasicCommand("create", InventorPartCreateService.class);
		super.addBasicCommand("update", InventorPartUpdateService.class);
		super.addBasicCommand("delete", InventorPartDeleteService.class);
	}

}
