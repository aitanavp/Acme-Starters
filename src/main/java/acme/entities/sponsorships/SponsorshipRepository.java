
package acme.entities.sponsorships;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface SponsorshipRepository extends AbstractRepository {

	@Query("select sum(p.money.amount) from Donation p where p.sponsorship.id = :sponsorshipId")
	Double computeSponsorshipMoney(int inventionId);
}
