
package acme.features.any.milestone;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Milestone;

@Repository
public interface AnyMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	public List<Milestone> findAllMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.id = :milestoneId")
	public Milestone findMilestoneById(int milestoneId);
}
