
package acme.entities.auditReports;

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
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.entities.auditors.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditReport extends AbstractEntity {

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
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
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
	//@Valid
	@Transient
	public Double getMonthsActive() {
		return MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);
	}


	@Transient
	@Autowired
	private AuditReportRepository repository;


	@Valid
	@Transient
	public Integer getHours() {
		Integer total;
		Integer hours;
		total = this.repository.computeAuditReportHours(this.getId());
		if (total == null)
			hours = 0;
		else
			hours = total;
		return hours;
	}

	// Relationships


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Auditor auditor;

}
