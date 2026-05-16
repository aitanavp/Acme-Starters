
package acme.entities.projects;

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
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.constraints.ValidHeader;
import acme.constraints.ValidKeywords;
import acme.constraints.ValidText;
import acme.realms.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	/**
	 * Serialisation version
	 */
	private static final long	serialVersionUID	= 1L;

	// Attributes

	@Mandatory
	@ValidHeader
	@Column
	private String				title;

	@Mandatory
	@ValidKeywords
	@Column
	private String				keywords;

	@Mandatory
	@ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				kickOffMoment;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				closeOutMoment;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	@Transient
	@Autowired
	private ProjectRepository	projectRepository;


	@Mandatory
	@ValidNumber(min = 0.0)
	@Transient
	public Double getEffort() {
		if (this.projectRepository == null || Boolean.TRUE.equals(this.draftMode))
			return 0.0;

		Integer people = this.projectRepository.countPeopleInvolved(this.getId());
		if (people == null || people == 0)
			return 0.0;

		double total = this.projectRepository.computeStrategyMonths(this.getId()) + this.projectRepository.computeInventionMonths(this.getId()) + this.projectRepository.computeCampaignMonths(this.getId())
			+ this.projectRepository.computeAuditReportMonths(this.getId()) + this.projectRepository.computeSponsorshipMonths(this.getId());

		return Math.round(total / people * 100.0) / 100.0;
	}

	// Relationships


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Manager manager;

}
