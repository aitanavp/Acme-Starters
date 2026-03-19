
package acme.entities.sponsorships;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface SponsorshipRepository extends AbstractRepository {

	@Query("select sum(p.money.amount) from Donation p where p.sponsorship.id = :sponsorshipId")
	Double computeSponsorshipMoney(int sponsorshipId);

	@Query("SELECT COUNT(d) from Donation d where d.sponsorship.id = :sponsorshipId")
	Long countDonationsBySponsorshipId(int sponsorshipId);

	@Query("SELECT s from Sponsorship s where s.ticker = :ticker")
	Sponsorship findSponsorshipByTicker(String ticker);

}
