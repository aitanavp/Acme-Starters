package acme.features.projectMember.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.Inventor;
import acme.realms.ProjectMember;


@Service
public class ProjectMemberInventionCreateService extends AbstractService<ProjectMember, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberInventionRepository	repository;

	private Project								project;

	private Invention							invention;

	private Collection<Invention>				availableInventions;

	// AbstractService interface ----------------------------------------------

	@Override
	public void authorise() {
	    boolean status;
	    int projectId;
	    ProjectMember projectMember;
	    Inventor Inventor;

	    projectId = super.getRequest().getData("projectId", int.class);
	    this.project = this.repository.findProjectById(projectId);

	    try {
	        projectMember = (ProjectMember) super.getRequest().getPrincipal().getRealmOfType(ProjectMember.class);
	    } catch (final Throwable e) {
	        projectMember = null;
	    }

	    try {
	        Inventor = (Inventor) super.getRequest().getPrincipal().getRealmOfType(Inventor.class);
	    } catch (final Throwable e) {
	        Inventor = null;
	    }

	    status = this.project != null && this.project.getDraftMode()
	        && projectMember != null && Inventor != null
	        && this.repository.isProjectMemberInProject(this.project.getId(), projectMember.getId());

	    super.setAuthorised(status);
	}

	@Override
	public void load() {
		Inventor inventor;

		if (this.project == null)
			this.project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));

		inventor = (Inventor) super.getRequest().getPrincipal().getRealmOfType(Inventor.class);

		this.availableInventions = this.repository.findAvailableInventionsByInventorId(inventor.getId(), this.project.getId());

		this.invention = null;
	}

	@Override
	public void bind() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = inventionId == 0 ? null : this.repository.findInventionById(inventionId);
	}

	@Override
	public void validate() {
		Inventor inventor;

		inventor = (Inventor) super.getRequest().getPrincipal().getRealmOfType(Inventor.class);

		super.state(this.invention != null, "inventionId", "project-member.invention.form.error.required");
		if (this.invention != null) {
			boolean isOwnInvention;
			boolean isUnassigned;

			isOwnInvention = this.invention.getInventor() != null
				&& this.invention.getInventor().getId() == inventor.getId();
			isUnassigned = this.invention.getProject() == null;

			super.state(isOwnInvention, "inventionId", "project-member.invention.form.error.owner");
			super.state(isUnassigned, "inventionId", "project-member.invention.form.error.assigned");
		}
	}

	@Override
	public void execute() {
		this.invention.setProject(this.project);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = new SelectChoices();
		choices.add("0", "----", this.invention == null);
		for (Invention availableInvention : this.availableInventions) {
			String key;
			String label;

			key = String.valueOf(availableInvention.getId());
			label = String.format("%s - %s", availableInvention.getTicker(), availableInvention.getName());
			choices.add(key, label, availableInvention.equals(this.invention));
		}
		if (this.invention != null && !this.availableInventions.contains(this.invention))
			choices.add(
				String.valueOf(this.invention.getId()),
				String.format("%s - %s", this.invention.getTicker(), this.invention.getName()),
				true
			);

		tuple = new Tuple();
		tuple.put("inventionId", choices.getSelected().getKey());
		tuple.put("inventionChoices", choices);

		super.getResponse().addData(tuple);

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}