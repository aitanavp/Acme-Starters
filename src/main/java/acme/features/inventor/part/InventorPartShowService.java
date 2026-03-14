
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.PartKind;
import acme.entities.inventions.Part;
import acme.entities.inventors.Inventor;

@Service
public class InventorPartShowService extends AbstractService<Inventor, Part> {

	// Internal state

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int id;
		Part part;

		id = super.getRequest().getData("id", int.class);
		part = this.repository.findPartById(id);

		status = part != null && part.getInvention().getInventor().isPrincipal();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);
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
