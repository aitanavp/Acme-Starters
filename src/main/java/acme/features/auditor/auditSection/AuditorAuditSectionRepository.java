
package acme.features.auditor.auditSection;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReports.AuditReport;
import acme.entities.auditReports.AuditSection;

@Repository
public interface AuditorAuditSectionRepository extends AbstractRepository {

	@Query("select a from AuditSection a where a.auditReport.id = :auditReportId")
	List<AuditSection> findAuditSectionsByAuditReportId(int auditReportId);

	@Query("select a from AuditSection a where a.id = :auditSectionId")
	AuditSection findAuditSectionById(int auditSectionId);

	@Query("select a from AuditReport a where a.id = :auditReportId")
	AuditReport findAuditReportById(int auditReportId);

	@Query("select count(a) from AuditSection a where a.auditReport.id = :auditReportId")
	int countByAuditReportId(int auditReportId);

}
