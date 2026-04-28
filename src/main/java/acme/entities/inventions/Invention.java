
package acme.entities.inventions;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidInvention;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.projects.Project;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidInvention
public class Invention extends AbstractEntity {

	/**
	 * Serialisation version
	 */
	private static final long	serialVersionUID	= 1L;

	// Attributes

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes


	@Mandatory
	// @Valid
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null || Boolean.TRUE.equals(this.draftMode))
			return 0.0;
		Double months = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);
		return Math.round(months * 10.0) / 10.0;
	}


	@Transient
	@Autowired
	private InventionRepository repository;


	@Mandatory
	@ValidMoney
	@Transient
	public Money getCost() {
		Double wrapper;
		Money result = new Money();
		result.setCurrency("EUR"); // Only Euros are accepted.

		if (this.repository == null) {
			result.setAmount(0.0);
			return result;
		}

		wrapper = this.repository.computeInventionCost(this.getId());
		if (wrapper == null)
			result.setAmount(0.0);
		else
			result.setAmount(Math.round(wrapper * 100.0) / 100.0);
		return result;
	}

	// Relationships


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Inventor inventor;
	
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Project project;

}
