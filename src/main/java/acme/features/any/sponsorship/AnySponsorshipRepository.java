
package acme.features.any.sponsorship;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

public interface AnySponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :sponsorshipId")
	public Sponsorship findSponsorshipById(int sponsorshipId);

	@Query("select s from Sponsorship s where s.draftMode = false")
	public List<Sponsorship> findAllPublishedSponsorships();

}
