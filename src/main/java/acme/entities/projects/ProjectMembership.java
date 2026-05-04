
package acme.entities.projects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.realms.ProjectMember;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectMembership extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	// Relationships

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private ProjectMember		projectMember;

}
