
package acme.features.any.campaign;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;

@Repository
public interface AnyCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :campaignId")
	public Campaign findCampaignById(int campaignId);

	@Query("select c from Campaign c where c.draftMode = false")
	public List<Campaign> findAllPublishedCampaigns();

}
