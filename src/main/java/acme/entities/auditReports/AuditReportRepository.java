
package acme.entities.auditReports;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select sum(a.hours) from AuditSection a where a.auditReport.id = :auditReportId")
	Integer computeAuditReportHours(int auditReportId);
}
