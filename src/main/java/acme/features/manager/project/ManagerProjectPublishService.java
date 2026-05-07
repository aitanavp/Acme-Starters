package acme.features.manager.project;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.features.inventor.part.InventorPartRepository;
import acme.realms.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;

	@Autowired
	private InventorPartRepository partRepository;

	private Project project;

	private Collection<Invention> inventions;

	@Override
	public void authorise() {
		boolean status;
		int id;
		Project project;

		id = super.getRequest().getData("id", int.class);
		project = this.repository.findProjectById(id);

		status = project != null && project.getDraftMode() && project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
		if (this.project != null)
			this.inventions = this.repository.findInventionsByProjectId(this.project.getId());
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
	}

	@Override
	public void validate() {
		if (this.project == null)
			return;

		super.validateObject(this.project);

		boolean hasInventions = this.inventions != null && !this.inventions.isEmpty();
		super.state(hasInventions, "*", "manager.project.publish.error.no-inventions");

		if (this.inventions == null)
			return;

		for (Invention invention : this.inventions) {
			// Avoid invoking the generic validator on the entity to prevent
			// runtime issues caused by entity-level derived getters using
			// injected resources. Perform the necessary checks here instead.

			boolean hasParts = this.partRepository.countByInventionId(invention.getId()) > 0;
			super.state(hasParts, "*", "manager.project.publish.error.no-parts");

			Date start = invention.getStartMoment();
			Date end = invention.getEndMoment();
			if (start != null && end != null)
				super.state(MomentHelper.isAfter(end, start), "endMoment", "manager.project.publish.error.end-after-start");

			if (start != null)
				super.state(MomentHelper.isFuture(start), "startMoment", "manager.project.publish.error.start-future");

			if (end != null)
				super.state(MomentHelper.isFuture(end), "endMoment", "manager.project.publish.error.end-future");

			// Validate cost/currency: ensure all parts use EUR and total >= 0
			boolean partsCurrencyOk = true;
			for (acme.entities.inventions.Part p : this.partRepository.findPartsByInventionId(invention.getId())) {
				if (p.getCost() == null || p.getCost().getCurrency() == null || !"EUR".equals(p.getCost().getCurrency())) {
					partsCurrencyOk = false;
					break;
				}
			}
			super.state(partsCurrencyOk, "*", "manager.project.publish.error.parts-currency");
		}
	}

	@Override
	public void execute() {
		if (this.inventions != null)
			for (Invention invention : this.inventions) {
				invention.setDraftMode(false);
				this.repository.save(invention);
			}

		this.project.setDraftMode(false);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "effort");
		super.unbindGlobal("managerId", this.project.getManager().getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}