
package acme.features.projectMember.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface ProjectMemberSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.project.id = :projectId")
	Collection<Sponsorship> findSponsorshipsByProjectId(int projectId);

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select case when count(pm) > 0 then true else false end from ProjectMembership pm where pm.project.id = :projectId and pm.projectMember.id = :projectMemberId")
	boolean isProjectMemberInProject(int projectId, int projectMemberId);

	//@Query("select p from Part p where p.invention.id = :inventionId")
	//Collection<Part> findPartsByInventionId(int inventionId);

}
