
package acme.features.any.advertisement;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.advertisements.Advertisement;

public class AnyAdvertisementShowService extends AbstractService<Any, Advertisement> {

	// Internal state

	@Autowired
	private AnyAdvertisementRepository	repository;

	private Advertisement				advertisement;

	// AbstractService interface


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Advertisement advertisement = this.repository.findAdvertisementById(id);

	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.advertisement = this.repository.findAdvertisementById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.advertisement, "slogan", "picture", "target");
	}
}
