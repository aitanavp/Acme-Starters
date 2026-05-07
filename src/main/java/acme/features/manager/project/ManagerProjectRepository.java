
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<acme.entities.inventions.Invention> findInventionsByProjectId(int projectId);

	@Query("select case when count(pm) > 0 then true else false end from ProjectMembership pm where pm.project.id = :projectId and pm.projectMember.id = :managerId")
	boolean isManagerInProject(int projectId, int managerId);
}
