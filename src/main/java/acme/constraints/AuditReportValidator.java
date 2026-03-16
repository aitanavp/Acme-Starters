
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.auditReports.AuditReport;
import acme.entities.auditReports.AuditReportRepository;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditReportRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidAuditReport annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AuditReport auditReport, final ConstraintValidatorContext context) {

		assert context != null;

		if (auditReport == null)
			return true;

		else {

			if (auditReport.getTicker() != null) {
				AuditReport existing = this.repository.findAuditReportByTicker(auditReport.getTicker());
				boolean uniqueTicker = existing == null || existing.getId() == auditReport.getId();
				super.state(context, uniqueTicker, "ticker", "acme.validation.auditReport.ticker.non-unique");
			}

			{
				if (auditReport.getDraftMode() != null && !auditReport.getDraftMode()) {
					Long auditSectionsCount = this.repository.countAuditSectionsByAuditReportId(auditReport.getId());
					boolean hasAuditSections = auditSectionsCount != null && auditSectionsCount > 0;

					super.state(context, hasAuditSections, "draftMode", "acme.validation.auditReport.auditSections.message");
				}
			}
			{
				Date now = MomentHelper.getBaseMoment();
				Date start = auditReport.getStartMoment();
				Date end = auditReport.getEndMoment();

				boolean differentMoments = start == null || end == null || !start.equals(end);
				super.state(context, differentMoments, "endMoment", "acme.validation.auditReport.start-end-not-equal.message");

				boolean validChronology = start == null || end == null || MomentHelper.isAfter(end, start) || start.equals(end);
				super.state(context, validChronology, "startMoment", "acme.validation.auditReport.start-before-end.message");

				boolean validDates = start != null && end != null && !MomentHelper.isBefore(start, now) && MomentHelper.isAfter(end, start);
				boolean validPublishedAuditReport = auditReport.getDraftMode() || validDates;

				super.state(context, validPublishedAuditReport, "*", "acme.validation.auditReport.dates.message");
			}
			{
				Date start = auditReport.getStartMoment();
				Date end = auditReport.getEndMoment();
				Double monthsActive = auditReport.getMonthsActive();
				boolean validChronology = start == null || end == null || MomentHelper.isAfter(end, start);
				boolean validMonths = !validChronology || monthsActive != null && monthsActive >= 0.0;

				super.state(context, validMonths, "monthsActive", "acme.validation.auditReport.monthsActive.message");
			}
		}

		return !super.hasErrors(context);
	}

}
