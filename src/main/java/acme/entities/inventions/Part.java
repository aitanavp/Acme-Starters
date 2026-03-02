
package acme.entities.inventions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoney;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.datatypes.PartKind;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Part extends AbstractEntity {

	/**
	 * Serialisation version
	 */
	private static final long	serialVersionUID	= 1L;

	// Attributes

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoney
	@Column
	private Money				cost; // Only Euros are accepted.

	@Mandatory
	@Valid
	@Column
	private PartKind			kind;

	// Derived attributes

	// Relationships

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Invention			invention;

}
