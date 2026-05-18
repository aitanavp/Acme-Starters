
package acme.features.any.project;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("select s from Project s where s.id=:id")
	Project findProjectById(int id);

	@Query("select s from Project s where s.draftMode = false")
	public List<Project> findAllPublishedProjects();
}
