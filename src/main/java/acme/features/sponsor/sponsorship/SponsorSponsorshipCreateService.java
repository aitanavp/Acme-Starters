
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface -----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsor sponsor;

		sponsor = (Sponsor) super.getRequest().getPrincipal().getActiveRealm();

		this.sponsorship = super.newObject(Sponsorship.class);
		this.sponsorship.setDraftMode(true);
		this.sponsorship.setSponsor(sponsor);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		Sponsor sponsor = (Sponsor) super.getRequest().getPrincipal().getActiveRealm();
		this.sponsorship.setSponsor(sponsor);
		this.sponsorship.setDraftMode(true);
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);
	}

	@Override
	public void execute() {
		this.repository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney");
	}
}
