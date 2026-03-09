
package acme.features.any.invention;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;

@Service
public class AnyInventionListService extends AbstractService<Any, Invention> {

	// Internal state

	@Autowired
	private AnyInventionRepository	repository;

	private List<Invention>			inventions;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost");
	}

	@Override
	public void load() {
		this.inventions = this.repository.findAllPublishedInventions();
	}

}
