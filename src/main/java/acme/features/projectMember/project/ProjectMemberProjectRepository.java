
package acme.features.projectMember.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface ProjectMemberProjectRepository extends AbstractRepository {

	@Query("select i from Invention i where i.inventor.id = :inventorId")
	Collection<Project> findProjectsByProjectMemberId(int inventorId);

	@Query("select i from Invention i where i.id = :id")
	Project findProjectById(int id);

	@Query("select case when count(pm) > 0 then true else false end from ProjectMembership pm where pm.project.id = :projectId and pm.projectMember.id = :projectMemberId")
	boolean isProjectMemberInProject(int projectId, int projectMemberId);

	//@Query("select p from Part p where p.invention.id = :inventionId")
	//Collection<Part> findPartsByInventionId(int inventionId);

}
