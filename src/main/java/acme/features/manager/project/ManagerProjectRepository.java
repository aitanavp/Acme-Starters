package acme.features.manager.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

    @Query("select p from Project p where p.id = :id")
    Project findProjectById(int id);

}
