
package acme.features.any.milestone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.helpers.MessageHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.features.any.campaign.AnyCampaignRepository;

@Service
public class AnyMilestoneListService extends AbstractService<Any, Milestone> {

	// Internal state

	@Autowired
	private AnyMilestoneRepository	repository;

	@Autowired
	AnyCampaignRepository			campaignRepository;

	private List<Milestone>			milestones;

	// AbstractService interface


	@Override
	public void authorise() {
		int campaignId = super.getRequest().getData("campaignId", int.class);
		Campaign campaign = this.campaignRepository.findCampaignById(campaignId);

		boolean status = campaign != null && !campaign.getDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void unbind() {
		for (Milestone milestone : this.milestones) {
			Tuple tuple;
			String code;

			tuple = super.unbindObject(milestone, "title", "achievements", "effort", "kind");
			code = String.format("spokesperson.milestone.kind.%s", milestone.getKind());
			tuple.put("kind", MessageHelper.getMessage(code));
		}
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("campaignId", int.class);
		this.milestones = this.repository.findPublishedMilestonesByCampaignId(id);
	}

}
