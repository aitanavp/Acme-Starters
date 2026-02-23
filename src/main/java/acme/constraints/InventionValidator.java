
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.inventions.Invention;
import acme.entities.inventions.InventionRepository;

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
				Invention existingInvention = this.repository.findInventionByTicker(invention.getTicker());
				boolean uniqueInvention = existingInvention != null && existingInvention.equals(invention);

				super.state(context, uniqueInvention, "ticker", "acme.validation.invention.ticker.non-unique");

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
				boolean validDates = start != null && end != null && !start.before(now) && end.after(start);

				boolean validPublishedInvention = invention.getDraftMode() || validDates;

				super.state(context, validPublishedInvention, "*", "acme.validation.invention.dates.message");
			}
		}

		return !super.hasErrors(context);
	}

}
