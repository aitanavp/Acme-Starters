
package acme.features.auditor.auditReport;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReports.AuditReport;
import acme.entities.auditReports.AuditSection;
import acme.entities.projects.Project;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.id = :auditReportId")
	AuditReport findAuditReportById(int auditReportId);

	@Query("select a from AuditReport a where a.auditor.id = :id")
	List<AuditReport> findAllAuditReportsByAuditorId(int id);

	@Query("select s from AuditSection s where s.auditReport.id = :auditReportId")
	Collection<AuditSection> findAuditSectionsByAuditReportId(int auditReportId);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findPublishedProjects();

	@Query("SELECT COUNT(s) FROM AuditSection s WHERE s.auditReport.id = :id")
	Integer getNumberOfAuditSectionsByAuditReportId(int id);

	@Query("SELECT COUNT(ar) > 0 FROM AuditReport ar WHERE ar.ticker = :ticker AND ar.id != :id ")
	boolean tickerExists(String ticker, int id);

}
