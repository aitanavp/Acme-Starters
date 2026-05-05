
package acme.features.any.advertisement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.advertisements.Advertisement;

public class AnyAdvertisementListService extends AbstractService<Any, Advertisement> {

	// Internal state

	@Autowired
	private AnyAdvertisementRepository	repository;

	private List<Advertisement>			advertisements;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.advertisements, "slogan", "picture", "target");
	}

	@Override
	public void load() {
		this.advertisements = this.repository.findAllAdvertisements();
	}

}
