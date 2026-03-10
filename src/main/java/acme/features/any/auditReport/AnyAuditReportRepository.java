
package acme.features.any.auditReport;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditReports.AuditReport;

@Repository
public interface AnyAuditReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.id = :auditReportId")
	public AuditReport findAuditReportById(int auditReportId);

	@Query("select a from AuditReport a where a.draftMode = false")
	public List<AuditReport> findAllPublishedAuditReports();

}
