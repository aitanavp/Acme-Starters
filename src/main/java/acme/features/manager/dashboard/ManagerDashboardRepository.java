
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(p) from Project p where p.manager.id = :managerId")
	Double totalNumberOfProjects(int managerId);

	@Query("""
		select count(p) - (select avg((select count(p2) from Project p2 where p2.manager.id = m.id)) from Manager m where m.id != :managerId)
		from Project p where p.manager.id = :managerId
		""")
	Double deviationOfProjects(int managerId);

	@Query("""
		select min(
		    (coalesce((select sum(function('timestampdiff', MONTH, s.startMoment, s.endMoment) * 1.0) from Strategy s where s.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, i.startMoment, i.endMoment) * 1.0) from Invention i where i.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, c.startMoment, c.endMoment) * 1.0) from Campaign c where c.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, a.startMoment, a.endMoment) * 1.0) from AuditReport a where a.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, sp.startMoment, sp.endMoment) * 1.0) from Sponsorship sp where sp.project.id = p.id), 0.0))
		    / nullif((select count(pm) from ProjectMembership pm where pm.project.id = p.id), 0)
		) from Project p where p.manager.id = :managerId
		""")
	Double minEffort(int managerId);

	@Query("""
		select max(
		    (coalesce((select sum(function('timestampdiff', MONTH, s.startMoment, s.endMoment) * 1.0) from Strategy s where s.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, i.startMoment, i.endMoment) * 1.0) from Invention i where i.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, c.startMoment, c.endMoment) * 1.0) from Campaign c where c.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, a.startMoment, a.endMoment) * 1.0) from AuditReport a where a.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, sp.startMoment, sp.endMoment) * 1.0) from Sponsorship sp where sp.project.id = p.id), 0.0))
		    / nullif((select count(pm) from ProjectMembership pm where pm.project.id = p.id), 0)
		) from Project p where p.manager.id = :managerId
		""")
	Double maxEffort(int managerId);

	@Query("""
		select avg(
		    (coalesce((select sum(function('timestampdiff', MONTH, s.startMoment, s.endMoment) * 1.0) from Strategy s where s.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, i.startMoment, i.endMoment) * 1.0) from Invention i where i.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, c.startMoment, c.endMoment) * 1.0) from Campaign c where c.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, a.startMoment, a.endMoment) * 1.0) from AuditReport a where a.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, sp.startMoment, sp.endMoment) * 1.0) from Sponsorship sp where sp.project.id = p.id), 0.0))
		    / nullif((select count(pm) from ProjectMembership pm where pm.project.id = p.id), 0)
		) from Project p where p.manager.id = :managerId
		""")
	Double averageEffort(int managerId);

	@Query("""
		select stddev(
		    (coalesce((select sum(function('timestampdiff', MONTH, s.startMoment, s.endMoment) * 1.0) from Strategy s where s.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, i.startMoment, i.endMoment) * 1.0) from Invention i where i.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, c.startMoment, c.endMoment) * 1.0) from Campaign c where c.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, a.startMoment, a.endMoment) * 1.0) from AuditReport a where a.project.id = p.id), 0.0)
		    + coalesce((select sum(function('timestampdiff', MONTH, sp.startMoment, sp.endMoment) * 1.0) from Sponsorship sp where sp.project.id = p.id), 0.0))
		    / nullif((select count(pm) from ProjectMembership pm where pm.project.id = p.id), 0)
		) from Project p where p.manager.id = :managerId
		""")
	Double deviationEffort(int managerId);

}
