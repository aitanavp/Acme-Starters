
package acme.features.administrator.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.advertisements.Advertisement;

@Service
public class AdministratorAdvertisementDeleteService extends AbstractService<Administrator, Advertisement> {

	@Autowired
	private AdministratorAdvertisementRepository	repository;

	private Advertisement							advertisement;


	@Override
	public void authorise() {
		boolean status;
		int id;
		Advertisement advertisement;

		id = super.getRequest().getData("id", int.class);
		advertisement = this.repository.findAdvertisementById(id);
		status = advertisement != null;
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.advertisement = this.repository.findAdvertisementById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.advertisement, "slogan", "picture", "target");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.delete(this.advertisement);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.advertisement, "slogan", "picture", "target");
	}
}
