package acme.features.administrator.advertisement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.advertisements.Advertisement;

@Service
public class AdministratorAdvertisementListService extends AbstractService<Administrator, Advertisement> {

	@Autowired
	private AdministratorAdvertisementRepository	repository;

	private List<Advertisement>				advertisements;

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		this.advertisements = this.repository.findAllAdvertisements();
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.advertisements, "slogan", "picture", "target", "draftMode");
	}
}
