
package acme.features.inventor.invention;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.features.inventor.part.InventorPartRepository;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	@Autowired
	private InventorPartRepository		partRepository;

	private Invention					invention;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Invention invention;

		id = super.getRequest().getData("id", int.class);
		invention = this.repository.findInventionById(id);

		status = invention != null && invention.getDraftMode() && invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		boolean hasParts = this.partRepository.countByInventionId(this.invention.getId()) > 0;
		super.state(hasParts, "*", "inventor.invention.publish.error.no-parts");

		Date start = this.invention.getStartMoment();
		Date end = this.invention.getEndMoment();
		if (start != null && end != null)
			super.state(MomentHelper.isAfter(end, start), "endMoment", "inventor.invention.publish.error.end-after-start");

		if (start != null)
			super.state(MomentHelper.isFuture(start), "startMoment", "inventor.invention.publish.error.start-future");

		if (end != null)
			super.state(MomentHelper.isFuture(end), "endMoment", "inventor.invention.publish.error.end-future");
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "cost", "draftMode");
		super.unbindGlobal("inventionId", this.invention.getInventor().getId());
	}

}
