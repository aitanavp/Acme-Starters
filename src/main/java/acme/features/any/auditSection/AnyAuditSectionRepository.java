
package acme.features.any.auditSection;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReports.AuditSection;

@Repository
public interface AnyAuditSectionRepository extends AbstractRepository {

	@Query("select a from AuditSection a where a.auditReport.id = :auditReportId and a.auditReport.draftMode = false")
	public List<AuditSection> findPublishedAuditSectionsByAuditReportId(int auditReportId);

	@Query("select a from AuditSection a where a.id = :auditSectionId")
	public AuditSection findAuditSectionById(int auditSectionId);
}
