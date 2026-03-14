
package acme.features.any.sponsorship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	// Internal state

	@Autowired
	private AnySponsorshipRepository	repository;

	private List<Sponsorship>			sponsorships;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sponsorships, "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney");
	}

	@Override
	public void load() {
		this.sponsorships = this.repository.findAllPublishedSponsorships();
	}

}
