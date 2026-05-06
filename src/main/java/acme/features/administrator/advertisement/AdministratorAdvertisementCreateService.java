
package acme.features.administrator.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.advertisements.Advertisement;

@Service
public class AdministratorAdvertisementCreateService extends AbstractService<Administrator, Advertisement> {

	@Autowired
	private AdministratorAdvertisementRepository	repository;

	private Advertisement							advertisement;


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		this.advertisement = super.newObject(Advertisement.class);
	}

	@Override
	public void bind() {
		super.bindObject(this.advertisement, "slogan", "picture", "target");
	}

	@Override
	public void validate() {
		super.validateObject(this.advertisement);
	}

	@Override
	public void execute() {
		this.repository.save(this.advertisement);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.advertisement, "slogan", "picture", "target");
	}
}
