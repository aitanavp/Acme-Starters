
package acme.features.sponsor.donation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationListService extends AbstractService<Sponsor, Donation> {

	// Internal state

	@Autowired
	private SponsorDonationRepository	repository;

	private List<Donation>				donations;

	private Sponsorship					sponsorship;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsorship = this.repository.findSponsorshipById(sponsorshipId);

		status = this.sponsorship != null && this.sponsorship.getSponsor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int sponsorshipId;

		if (this.sponsorship == null) {
			sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
			this.sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		}

		this.donations = this.repository.findDonationsBySponsorshipId(this.sponsorship.getId());
	}

	@Override
	public void unbind() {
		for (Donation donation : this.donations) {
			Tuple tuple;
			String code;

			tuple = super.unbindObject(donation, "name", "notes", "money", "kind");
			code = String.format("sponsor.donation.kind.%s", donation.getKind());
			tuple.put("kind", MessageHelper.getMessage(code));
		}
		super.unbindGlobal("sponsorshipId", this.sponsorship.getId());
		super.unbindGlobal("draftMode", this.sponsorship.getDraftMode());
	}

}
