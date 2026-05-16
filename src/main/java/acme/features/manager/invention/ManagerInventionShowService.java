
package acme.features.manager.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Manager;

@Service
public class ManagerInventionShowService extends AbstractService<Manager, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface ---------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Manager principal;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
		principal = (Manager) super.getRequest().getPrincipal().getRealmOfType(Manager.class);
		status = this.invention != null && this.invention.getProject() != null && principal != null && this.invention.getProject().getManager().getId() == principal.getId();
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "cost");
		super.unbindGlobal("inventorId", this.invention.getInventor().getId());
	}

}
