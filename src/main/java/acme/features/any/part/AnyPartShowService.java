
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;

@Service
public class AnyPartShowService extends AbstractService<Any, Part> {

	// Internal state

	@Autowired
	private AnyPartRepository	repository;

	private Part				part;

	// AbstractService interface


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Part part = this.repository.findPartById(id);

		boolean status = part != null && part.getInvention() != null && !part.getInvention().getDraftMode();
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
		super.unbindObject(this.part, "name", "description", "cost", "kind");
	}
}
