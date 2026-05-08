package acme.features.projectMember.invention;
 
import java.util.Collection;
 
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
 
import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
 
@Repository
public interface ProjectMemberInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> findInventionsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select i from Invention i where i.id = :id")
	Invention findInventionById(int id);

	@Query("select case when count(pm) > 0 then true else false end " //
		+ "from ProjectMembership pm " //
		+ "where pm.project.id = :projectId " //
		+ "and pm.projectMember.id = :projectMemberId")
	boolean isProjectMemberInProject(int projectId, int projectMemberId);

	@Query("select case when count(pm) > 0 then true else false end " //
		+ "from ProjectMembership pm " //
		+ "where pm.project.id = :projectId " //
		+ "and pm.projectMember.userAccount.id = " //
		+ "(select i.userAccount.id from Inventor i where i.id = :inventorId)")
	boolean isInventorInProject(int projectId, int inventorId);

	// Devuelve inventos del inventor que NO están asignados al proyecto dado
	// (ni a ningún otro proyecto si quieres exclusividad total — ajusta según regla de negocio)
	@Query("select i from Invention i " //
		+ "where i.inventor.id = :inventorId " //
		+ "and (i.project is null or i.project.id != :projectId)")
	Collection<Invention> findAvailableInventionsByInventorId(int inventorId, int projectId);

	// Versión sin projectId: inventos del inventor sin proyecto asignado
	@Query("select i from Invention i where i.inventor.id = :inventorId and i.project is null")
	Collection<Invention> findAvailableInventionsByInventorId(int inventorId);

	@Query("""
		SELECT COUNT(i) > 0
		FROM Invention i
		WHERE i.id = :inventionId
			AND i.inventor.id = :inventorId
			AND (i.project IS NULL OR i.project.id != :projectId)
			AND i.draftMode = true
		""")
	boolean isInventionAvailableForProject(int inventionId, int inventorId, int projectId);

}