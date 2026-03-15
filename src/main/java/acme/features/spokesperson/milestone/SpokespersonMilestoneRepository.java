
package acme.features.spokesperson.milestone;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Repository
public interface SpokespersonMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	List<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.id = :milestoneId")
	Milestone findMilestoneById(int milestoneId);

	@Query("select c from Campaign c where c.id = :campaignId")
	Campaign findCampaignById(int campaignId);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	int countByCampaignId(int campaignId);

	@Query("select coalesce(sum(m.effort), 0) from Milestone m where m.campaign.id = :campaignId")
	Double sumEffortByCampaignId(int campaignId);

}
