
package acme.features.sponsor.sponsorship;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.features.sponsor.donation.SponsorDonationRepository;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	@Autowired
	private SponsorDonationRepository		donationRepository;

	private Sponsorship						sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Sponsorship sponsorship;

		id = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findSponsorshipById(id);

		status = sponsorship != null && sponsorship.getDraftMode() && sponsorship.getSponsor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);

		boolean hasDonations = this.donationRepository.countBySponsorshipId(this.sponsorship.getId()) > 0;
		super.state(hasDonations, "*", "sponsor.sponsorship.publish.error.no-donations");

		Date start = this.sponsorship.getStartMoment();
		Date end = this.sponsorship.getEndMoment();
		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "sponsor.sponsorship.publish.error.end-after-start");

		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "sponsor.sponsorship.publish.error.start-future");

		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "sponsor.sponsorship.publish.error.end-future");
	}

	@Override
	public void execute() {
		this.sponsorship.setDraftMode(false);
		this.repository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney");
		super.unbindGlobal("sponsorId", this.sponsorship.getSponsor().getId());
	}

}
