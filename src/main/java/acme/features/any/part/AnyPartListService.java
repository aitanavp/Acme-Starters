
package acme.features.any.part;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	// Internal state

	@Autowired
	private AnyPartRepository	repository;

	private List<Part>			parts;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("inventionId", int.class);
		this.parts = this.repository.findAllPartsByInventionId(id);
	}

}
