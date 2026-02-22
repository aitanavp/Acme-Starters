
package acme.entities.campaigns;

import static acme.client.components.validation.ValidMoment.Constraint.ENFORCE_FUTURE;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Moment;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.entities.spokespersons.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Campaign extends AbstractEntity {

	/**
	 * Serialisation version
	 */
	private static final long	serialVersionUID	= 1L;

	// Attributes

	@Mandatory
	//@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	//@ValidHeader
	@Column
	private String				name;

	@Mandatory
	//@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = ENFORCE_FUTURE)
	//@Temporal(TemporalType.TIMESTAMP)
	private Moment				startMoment;

	@Mandatory
	@ValidMoment(constraint = ENFORCE_FUTURE)
	//@Temporal(TemporalType.TIMESTAMP)
	private Moment				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes


	@Valid
	@Transient
	public Double getMonthsActive() {
		Duration duration = MomentHelper.computeDuration(this.startMoment, this.endMoment);
		long days = duration.toDays();
		double months = days / 30.0;
		return months < 0 ? 0.0 : months;
	}


	@Transient
	@Autowired
	private CampaignRepository repository;


	@Transient
	private Double getEffort() {
		double result;
		result = this.repository.computeEffort(this.getId());
		return result;

	}

	// Relationships


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Spokesperson managedBy;

}
