
package acme.features.sponsor.sponsorship;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
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
		Date start = this.sponsorship.getStartMoment();
		Date end = this.sponsorship.getEndMoment();
		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "sponsor.sponsorship.form.error.end-after-start");

		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "sponsor.sponsorship.form.error.start-future");

		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "sponsor.sponsorship.form.error.end-future");
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
