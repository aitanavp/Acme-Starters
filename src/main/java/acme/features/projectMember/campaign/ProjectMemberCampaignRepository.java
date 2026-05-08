
package acme.features.projectMember.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;

@Repository
public interface ProjectMemberCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select case when count(pm) > 0 then true else false end from ProjectMembership pm where pm.project.id = :projectId and pm.projectMember.id = :projectMemberId")
	boolean isProjectMemberInProject(int projectId, int projectMemberId);

	//@Query("select p from Part p where p.invention.id = :inventionId")
	//Collection<Part> findPartsByInventionId(int inventionId);
	
	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId and c.project is null")
	Collection<Campaign> findAvailableCampaignsBySpokespersonId(int spokespersonId);
	
	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId and (c.project is null or c.project.id != :projectId)")
	Collection<Campaign> findAvailableCampaignsBySpokespersonId(int spokespersonId, int projectId);


}
