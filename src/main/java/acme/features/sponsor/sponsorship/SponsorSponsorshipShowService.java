
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship>

{

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface


	@Override
	public void authorise() {
		boolean status;
		status = this.sponsorship != null && this.sponsorship.getSponsor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		Collection<Project> projects = this.repository.findPublishedProjects();
		choices = SelectChoices.from(projects, "title", this.sponsorship.getProject());

		double months = this.sponsorship.getMonthsActive();
		Money money = this.sponsorship.getTotalMoney();
		tuple = super.unbindObject(this.sponsorship, //
			"ticker", "name", "description", "startMoment", //
			"endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("totalMoney", money);
		tuple.put("project", choices);
	}
}
