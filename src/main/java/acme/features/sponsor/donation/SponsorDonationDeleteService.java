
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.datatypes.DonationKind;
import acme.entities.sponsorships.Donation;
import acme.realms.Sponsor;

@Service
public class SponsorDonationDeleteService extends AbstractService<Sponsor, Donation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDonationRepository	repository;

	private Donation					donation;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Donation donation;

		id = super.getRequest().getData("id", int.class);
		donation = this.repository.findDonationById(id);

		status = donation != null && donation.getSponsorship().getDraftMode() && donation.getSponsorship().getSponsor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.donation = this.repository.findDonationById(id);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.delete(this.donation);
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
