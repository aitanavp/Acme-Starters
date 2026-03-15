
package acme.features.inventor.part;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	// Internal state

	@Autowired
	private InventorPartRepository	repository;

	private List<Part>				parts;

	private Invention				invention;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repository.findInventionById(inventionId);

		status = this.invention != null && this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int inventionId;

		if (this.invention == null) {
			inventionId = super.getRequest().getData("inventionId", int.class);
			this.invention = this.repository.findInventionById(inventionId);
		}

		this.parts = this.repository.findPartsByInventionId(this.invention.getId());
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
		super.unbindGlobal("inventionId", this.invention.getId());
		super.unbindGlobal("draftMode", this.invention.getDraftMode());
	}

}
