
package acme.features.any.campaign;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class AnyCampaignListService extends AbstractService<Any, Campaign> {

	// Internal state

	@Autowired
	private AnyCampaignRepository	repository;

	private List<Campaign>			campaigns;

	// AbstractService interface


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, "name", "description");
	}

	@Override
	public void load() {
		this.campaigns = this.repository.findAllPublishedCampaigns();
	}

}
