
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

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberSponsorshipRepository	repository;

	private Collection<Sponsorship>				sponsorships;

	private Project								project;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int projectId;

		if (this.project == null) {
			projectId = super.getRequest().getData("projectId", int.class);
			this.project = this.repository.findProjectById(projectId);
		}

		this.sponsorships = this.repository.findSponsorshipsByProjectId(this.project.getId());
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.sponsorships, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "totalMoney", "draftMode");
	}

}
