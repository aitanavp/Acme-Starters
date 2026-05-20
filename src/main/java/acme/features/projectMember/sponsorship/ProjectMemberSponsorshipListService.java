
package acme.features.projectMember.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.ProjectMember;

@Service
public class ProjectMemberSponsorshipListService extends AbstractService<ProjectMember, Sponsorship> {

	@Autowired
	private ProjectMemberSponsorshipRepository	repository;
	private Collection<Sponsorship>				sponsorships;
	private Project								project;


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		ProjectMember projectMember;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		try {
			projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
		} catch (final Throwable e) {
			projectMember = null;
		}
		status = this.project != null && projectMember != null && this.repository.isProjectMemberInProject(this.project.getId(), projectMember.getId());
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
		if (this.project == null)
			return;
		this.sponsorships = this.repository.findSponsorshipsByProjectId(this.project.getId());
	}

	@Override
	public void unbind() {
		if (this.sponsorships == null)
			return;
		super.unbindObjects(this.sponsorships, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney", "draftMode");
	}
}
