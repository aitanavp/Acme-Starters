
package acme.features.manager.project;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<acme.entities.inventions.Invention> findInventionsByProjectId(int projectId);

	@Query("select case when count(pm) > 0 then true else false end from ProjectMembership pm where pm.project.id = :projectId and pm.projectMember.id = :managerId")
	boolean isManagerInProject(int projectId, int managerId);

	@Query("select min(s.startMoment) from Strategy s where s.project.id = :projectId")
	Date findEarliestStrategyStart(int projectId);

	@Query("select min(i.startMoment) from Invention i where i.project.id = :projectId")
	Date findEarliestInventionStart(int projectId);

	@Query("select min(c.startMoment) from Campaign c where c.project.id = :projectId")
	Date findEarliestCampaignStart(int projectId);

	@Query("select max(s.endMoment) from Strategy s where s.project.id = :projectId")
	Date findLatestStrategyEnd(int projectId);

	@Query("select max(i.endMoment) from Invention i where i.project.id = :projectId")
	Date findLatestInventionEnd(int projectId);

	@Query("select max(c.endMoment) from Campaign c where c.project.id = :projectId")
	Date findLatestCampaignEnd(int projectId);

	@Query("select s from Strategy s where s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProjectId(int projectId);

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);
}
