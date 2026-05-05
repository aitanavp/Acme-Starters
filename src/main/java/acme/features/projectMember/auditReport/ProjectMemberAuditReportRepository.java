
package acme.features.projectMember.auditReport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReports.AuditReport;
import acme.entities.projects.Project;

@Repository
public interface ProjectMemberAuditReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.project.id = :projectId")
	Collection<AuditReport> findAuditReportsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select a from AuditReport a where a.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select case when count(pm) > 0 then true else false end from ProjectMembership pm where pm.project.id = :projectId and pm.projectMember.id = :projectMemberId")
	boolean isProjectMemberInProject(int projectId, int projectMemberId);

	//@Query("select p from Part p where p.invention.id = :inventionId")
	//Collection<Part> findPartsByInventionId(int inventionId);

}
