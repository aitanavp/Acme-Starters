
package acme.entities.strategies;

import static acme.client.components.validation.ValidMoment.Constraint.ENFORCE_FUTURE;

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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidStrategy;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidStrategy
public class Strategy extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//Attributes

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
	@ValidMoment(constraint = ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = ENFORCE_FUTURE)
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

	//Derived attributes


	@Valid
	@Transient
	public Double getMonthsActive() {
		Double result;

		if (this.startMoment == null || this.endMoment == null)
			result = 0.0;
		else
			result = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);

		return result;
	}


	@Transient
	@Autowired
	private StrategyRepository repository;


	@Mandatory
	@ValidScore
	@Transient
	public Double getExpectedPercentage() {
		Double result;
		result = this.repository.computeExpectedPercentage(this.getId());
		if (result == null)
			result = 00.00;
		return result;
	}

	// Relationships


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser fundraiser;
}
