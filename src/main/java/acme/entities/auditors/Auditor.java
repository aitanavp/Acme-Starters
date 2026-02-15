
package acme.entities.auditors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Auditor extends AbstractRole {

	/**
	 * Serialisation version
	 */
	private static final long	serialVersionUID	= 1L;

	//Atributes

	@Mandatory
	//@ValidHeader
	@Column
	private String				firm;

	@Mandatory
	//@ValidHeader
	@Column
	private String				highlights;

	@Mandatory
	@Valid
	@Column
	private Boolean				solicitor;

}
