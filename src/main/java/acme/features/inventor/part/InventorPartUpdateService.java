
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.PartKind;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Service
public class InventorPartUpdateService extends AbstractService<Inventor, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Part part;

		id = super.getRequest().getData("id", int.class);
		part = this.repository.findPartById(id);

		status = part != null && part.getInvention().getDraftMode() && part.getInvention().getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
		if (this.part.getCost() != null && this.part.getCost().getCurrency() != null)
			super.state("EUR".equals(this.part.getCost().getCurrency()), "cost", "inventor.part.form.error.currency");
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
