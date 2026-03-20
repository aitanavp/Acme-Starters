
package acme.features.any.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

public class AnySponsorshipShowService extends AbstractService<Any, Sponsorship> {

	// Internal state

	@Autowired
	private AnySponsorshipRepository	repository;

	private Sponsorship					sponsorship;

	// AbstractService interface


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Sponsorship sponsorship = this.repository.findSponsorshipById(id);
		boolean status = sponsorship != null && !sponsorship.getDraftMode();
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney");
		super.unbindGlobal("sponsorId", this.sponsorship.getSponsor().getId());
	}
}
