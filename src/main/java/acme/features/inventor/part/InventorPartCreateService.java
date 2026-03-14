
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.PartKind;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.entities.inventors.Inventor;

public class InventorPartCreateService extends AbstractService<Inventor, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;

	private Invention				invention;

	// AbstractService interface -----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repository.findInventionById(inventionId);

		status = this.invention != null && this.invention.getDraftMode() && this.invention.getInventor().isPrincipal();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int inventionId;

		if (this.invention == null) {
			inventionId = super.getRequest().getData("inventionId", int.class);
			this.invention = this.repository.findInventionById(inventionId);
		}

		this.part = super.newObject(Part.class);
		this.part.setInvention(this.invention);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(PartKind.class, this.part.getKind());
		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");
		tuple.put("kind", choices.getSelected().getKey());
		tuple.put("PartKind", choices);

		super.unbindGlobal("inventionId", this.part.getInvention().getId());
		super.unbindGlobal("draftMode", this.part.getInvention().getDraftMode());
	}

}
