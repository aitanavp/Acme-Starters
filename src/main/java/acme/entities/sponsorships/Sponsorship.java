
package acme.entities.sponsorships;

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
import org.springframework.format.annotation.DateTimeFormat;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidSponsorship;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.projects.Project;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidSponsorship
public class Sponsorship extends AbstractEntity {

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
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
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


	@Valid
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null || Boolean.TRUE.equals(this.draftMode))
			return 0.0;
		Double months = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);
		return Math.round(months * 10.0) / 10.0;
	}


	@Transient
	@Autowired
	private SponsorshipRepository repository;


	@Transient
	public Money getTotalMoney() {
		Double wrapper;
		Money result = new Money();
		result.setCurrency("EUR"); // Only Euros are accepted.

		wrapper = this.repository.computeSponsorshipMoney(this.getId());
		if (wrapper == null)
			result.setAmount(0.0);
		else
			result.setAmount(wrapper);
		return result;
	}

	// Relationships


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;
	
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Project project;

}
