
package acme.entities.auditReports;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select sum(a.hours) from AuditSection a where a.auditReport.id = :auditReportId")
	Integer computeAuditReportHours(int auditReportId);

	@Query("select count(a) from AuditSection a where a.auditReport.id = :auditReportId")
	Long countAuditSectionsByAuditReportId(int auditReportId);

	@Query("select a from AuditReport a where a.ticker=:ticker")
	AuditReport findAuditReportByTicker(final String ticker);
}
