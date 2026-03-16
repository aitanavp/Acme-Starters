
package acme.entities.campaigns;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface CampaignRepository extends AbstractRepository {

	@Query("select coalesce(sum(m.effort), 0) from Milestone m where m.campaign.id = :campaignId")
	Double computeEffort(int campaignId);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	Long countMilestonesByCampaignId(int campaignId);

	@Query("select c from Campaign c where c.ticker=:ticker")
	Campaign findCampaignByTicker(final String ticker);
}
