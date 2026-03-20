
package acme.features.sponsor.sponsorship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipListService extends AbstractService<Sponsor, Sponsorship> {
	// Internal state

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private List<Sponsorship>				sponsorships;

	// AbstractService interface


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.sponsorships = this.repository.findAllSponsorshipBySponsorId(id);
	}

	@Override
	public void unbind() {
		for (Sponsorship sponsorship : this.sponsorships) {
			Tuple tuple;

			tuple = super.unbindObject(sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney", "draftMode");
			tuple.put("draftMode", sponsorship.getDraftMode() ? "☑" : "☒");
		}
	}
}
