
package acme.features.manager.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerCampaignListService extends AbstractService<Manager, Campaign> {

	@Autowired
	private ManagerCampaignRepository	repository;
	private Collection<Campaign>		campaigns;
	private Project						project;


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
		this.campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
	}

	@Override
	public void unbind() {
		if (this.campaigns == null)
			return;
		super.unbindObjects(this.campaigns, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "effort", "draftMode");
	}
}
