
package acme.features.inventor.invention;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionCreateService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		Inventor inventor;
		inventor = (Inventor) super.getRequest().getPrincipal().getActiveRealm();
		this.invention = super.newObject(Invention.class);
		this.invention.setDraftMode(true);
		this.invention.setInventor(inventor);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		Inventor inventor = (Inventor) super.getRequest().getPrincipal().getActiveRealm();
		this.invention.setInventor(inventor);
		this.invention.setDraftMode(true);
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
		Date start = this.invention.getStartMoment();
		Date end = this.invention.getEndMoment();
		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "inventor.invention.form.error.end-after-start");

		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "inventor.invention.form.error.start-future");

		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "inventor.invention.form.error.end-future");
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost");
	}

}
