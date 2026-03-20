
package acme.features.any.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.features.any.sponsorship.AnySponsorshipRepository;

@Service
public class AnyDonationListService extends AbstractService<Any, Donation> {

	//Internal state
	@Autowired
	private AnyDonationRepository		repository;
	private Collection<Donation>		donation;
	@Autowired
	private AnySponsorshipRepository	sponsorshipRepository;


	//AbstractService interface
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("sponsorshipId", int.class);
		this.donation = this.repository.findAllDonationBySponsorshipId(id);
	}

	@Override
	public void authorise() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		Sponsorship sponsorship = this.sponsorshipRepository.findSponsorshipById(sponsorshipId);

		boolean status = sponsorship != null && !sponsorship.getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.donation, "name", "notes", "money", "kind");
	}

}
