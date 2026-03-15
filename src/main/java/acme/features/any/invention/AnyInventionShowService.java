
package acme.features.any.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	// Internal state

	@Autowired
	private AnyInventionRepository	repository;

	private Invention				invention;

	// AbstractService interface


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Invention invention = this.repository.findInventionById(id);
		boolean status = invention != null && !invention.getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost");
		super.unbindGlobal("inventorId", this.invention.getInventor().getId());
	}
}
