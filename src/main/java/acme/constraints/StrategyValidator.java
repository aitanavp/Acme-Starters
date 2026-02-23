
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.StrategyRepository;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	//Internal State
	@Autowired
	private StrategyRepository repository;


	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {

		assert context != null;

		if (strategy == null)
			return true;

		else {
			{
				Strategy existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				boolean uniqueStrategy = existingStrategy != null && existingStrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.ticker.non-unique");
			}
			{
				if (strategy.getDraftMode() != null && !strategy.getDraftMode()) {
					Double partsCount = this.repository.countTacticsOfStrategy(strategy.getId());
					boolean hasParts = partsCount != null && partsCount > 0;

					super.state(context, hasParts, "draftMode", "acme.validation.strategy.parts.message");
				}
			}
			{
				Date now = MomentHelper.getBaseMoment();
				Date start = strategy.getStartMoment();
				Date end = strategy.getEndMoment();
				boolean validDates = start != null && end != null && !start.before(now) && end.after(start);

				boolean validPublishedInvention = strategy.getDraftMode() || validDates;

				super.state(context, validPublishedInvention, "*", "acme.validation.invention.dates.message");
			}
		}

		return !super.hasErrors(context);

	}
}
