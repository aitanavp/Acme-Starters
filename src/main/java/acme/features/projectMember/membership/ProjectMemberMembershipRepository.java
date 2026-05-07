package acme.features.projectMember.membership;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMembership;
import acme.realms.ProjectMember;

@Repository
public interface ProjectMemberMembershipRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select pm from ProjectMembership pm where pm.project.id = :projectId")
	Collection<ProjectMembership> findAllProjectMembershipsByProjectId(int projectId);

	@Query("select pm from ProjectMembership pm where pm.project.id = :projectId and pm.projectMember.id = :projectMemberId")
	ProjectMembership findProjectMembershipByProjectIdAndProjectMemberId(int projectId, int projectMemberId);

	@Query("select distinct pm from ProjectMember pm where (pm.userAccount.id in (select i.userAccount.id from Inventor i) or pm.userAccount.id in (select f.userAccount.id from Fundraiser f) or pm.userAccount.id in (select s.userAccount.id from Spokesperson s)) and pm.id not in (select membership.projectMember.id from ProjectMembership membership where membership.project.id = :projectId)")
	Collection<ProjectMember> findAvailableProjectMembersByProjectId(int projectId);

	@Query("select pm from ProjectMember pm where pm.userAccount.id = :userAccountId")
	ProjectMember findProjectMemberByUserAccountId(int userAccountId);

}