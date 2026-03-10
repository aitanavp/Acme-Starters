
package acme.features.any.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.fundraisers.Fundraiser;

@Service
public class AnyFundraiserShowService extends AbstractService<Any, Fundraiser> {

	// Internal state
	@Autowired
	private AnyFundraiserRepository	repository;

	private Fundraiser				fundraiser;


	// AbstractService interface
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.fundraiser = this.repository.findFundraiserById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.fundraiser, "bank", "statement", "agent");
	}
}
