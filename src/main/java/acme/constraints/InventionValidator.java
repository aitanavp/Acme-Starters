
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.datatypes.Money;
import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.inventions.Invention;
import acme.entities.inventions.InventionRepository;
import acme.entities.inventions.Part;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {

		assert context != null;

		if (invention == null)
			return true;

		else {
			{
				if (invention.getTicker() != null) {
					Invention existing = this.repository.findInventionByTicker(invention.getTicker());
					boolean uniqueTicker = existing == null || existing.getId() == invention.getId();
					super.state(context, uniqueTicker, "ticker", "acme.validation.invention.ticker.non-unique");
				}

			}
			{
				if (invention.getDraftMode() != null && !invention.getDraftMode()) {
					Long partsCount = this.repository.countPartsByInventionId(invention.getId());
					boolean hasParts = partsCount != null && partsCount > 0;

					super.state(context, hasParts, "draftMode", "acme.validation.invention.parts.message");
				}
			}
			{
				Date now = MomentHelper.getBaseMoment();
				Date start = invention.getStartMoment();
				Date end = invention.getEndMoment();

				boolean validDates = start != null && end != null && !MomentHelper.isBefore(start, now) && MomentHelper.isAfter(end, start);
				boolean validPublishedInvention = invention.getDraftMode() || validDates;

				super.state(context, validPublishedInvention, "*", "acme.validation.invention.dates.message");
			}
			{
				Double monthsActive = invention.getMonthsActive();
				boolean validMonths = Boolean.TRUE.equals(invention.getDraftMode()) || monthsActive >= 0.0;

				super.state(context, validMonths, "monthsActive", "acme.validation.invention.monthsActive.message");
			}
			{
				Money cost = invention.getCost();
				boolean validCost = cost != null && cost.getAmount() != null && cost.getAmount() >= 0.0 && "EUR".equals(cost.getCurrency());

				super.state(context, validCost, "cost", "acme.validation.invention.cost.message");
			}
			{
				Boolean validParts = true;
				for (Part p : this.repository.findPartsByInventionId(invention.getId()))
					validParts = "EUR".equals(p.getCost().getCurrency());

				super.state(context, validParts, "*", "acme.validation.invention.parts.message");
			}
		}

		return !super.hasErrors(context);
	}

}
