
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.DonationKind;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

public class SponsorDonationCreateService extends AbstractService<Sponsor, Donation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDonationRepository	repository;

	private Donation					donation;

	private Sponsorship					sponsorship;

	// AbstractService interface -----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsorship = this.repository.findSponsorshipById(sponsorshipId);

		status = this.sponsorship != null && this.sponsorship.getDraftMode() && this.sponsorship.getSponsor().isPrincipal();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int sponsorshipId;

		if (this.sponsorship == null) {
			sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
			this.sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		}

		this.donation = super.newObject(Donation.class);
		this.donation.setSponsorship(this.sponsorship);
	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.donation);
		if (this.donation.getMoney() != null && this.donation.getMoney().getCurrency() != null)
			super.state("EUR".equals(this.donation.getMoney().getCurrency()), "money", "inventor.donation.form.error.currency");
	}

	@Override
	public void execute() {
		this.repository.save(this.donation);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(DonationKind.class, this.donation.getKind());
		tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tuple.put("kind", choices.getSelected().getKey());
		tuple.put("DonationKind", choices);

		super.unbindGlobal("sponsorshipId", this.donation.getSponsorship().getId());
		super.unbindGlobal("draftMode", this.donation.getSponsorship().getDraftMode());
	}

}
