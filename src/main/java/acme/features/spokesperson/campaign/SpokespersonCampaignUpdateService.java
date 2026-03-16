
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignUpdateService extends AbstractService<Spokesperson, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Campaign campaign;

		id = super.getRequest().getData("id", int.class);
		campaign = this.repository.findCampaignById(id);

		status = campaign != null && campaign.getDraftMode() && campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "effort");
		super.unbindGlobal("spokespersonId", this.campaign.getSpokesperson().getId());
		super.unbindGlobal("draftMode", this.campaign.getDraftMode());
	}

}
