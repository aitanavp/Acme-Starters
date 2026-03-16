
package acme.features.sponsor.donation;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorDonationRepository extends AbstractRepository {

	@Query("select d from Donation d where d.sponsorship.id = :sponsorshipId")
	List<Donation> findDonationsBySponsorshipId(int sponsorshipId);

	@Query("select d from Donation d where d.id = :donationId")
	Donation findDonationById(int donationId);

	@Query("select s from Sponsorship s where s.id = :sponsorshipId")
	Sponsorship findSponsorshipById(int sponsorshipId);

	@Query("select count(d) from Donation d where d.sponsorship.id = :sponsorshipId")
	int countBySponsorshipId(int sponsorshipId);

	@Query("select coalesce(sum(d.money.amount), 0) from Donation d where d.sponsorship.id = :sponsorshipId and d.money.currency = 'EUR'")
	Double sumAmountEurBySponsorshipId(int sponsorshipId);

}
