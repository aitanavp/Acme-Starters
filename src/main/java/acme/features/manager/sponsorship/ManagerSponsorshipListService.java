
package acme.features.manager.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Manager;

@Service
public class ManagerSponsorshipListService extends AbstractService<Manager, Sponsorship> {

	@Autowired
	private ManagerSponsorshipRepository	repository;
	private Collection<Sponsorship>			sponsorships;
	private Project							project;


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);
		status = project != null && project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
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
