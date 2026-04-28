package acme.entities.projects;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import acme.client.repositories.AbstractRepository;

@Repository
public interface ProjectRepository extends AbstractRepository {

	@Query("select coalesce(sum(cast(function('timestampdiff', MONTH, m.startMoment, m.endMoment) as double)), 0.0) from Campaign m where m.project.id = :projectId")
	Double computeCampaignMonths(int projectId);

	@Query("select coalesce(sum(cast(function('timestampdiff', MONTH, m.startMoment, m.endMoment) as double)), 0.0) from Strategy m where m.project.id = :projectId")
	Double computeStrategyMonths(int projectId);

	@Query("select coalesce(sum(cast(function('timestampdiff', MONTH, m.startMoment, m.endMoment) as double)), 0.0) from Invention m where m.project.id = :projectId")
	Double computeInventionMonths(int projectId);
	
	@Query("select coalesce(sum(cast(function('timestampdiff', MONTH, m.startMoment, m.endMoment) as double)), 0.0) from AuditReport m where m.project.id = :projectId")
	Double computeAuditReportMonths(int projectId);
	
	@Query("select coalesce(sum(cast(function('timestampdiff', MONTH, m.startMoment, m.endMoment) as double)), 0.0) from Sponsorship m where m.project.id = :projectId")
	Double computeSponsorshipMonths(int projectId);

	@Query("select count(pm) from ProjectMember pm where pm.project.id = :projectId")
	Integer countPeopleInvolved(int projectId);

}
