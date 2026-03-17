
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
				if (strategy.getTicker() != null && !strategy.getTicker().isBlank()) {
					Strategy existing = this.repository.findStrategyByTicker(strategy.getTicker());
					boolean uniqueTicker = existing == null || existing.getId() == strategy.getId();
					super.state(context, uniqueTicker, "ticker", "acme.validation.strategy.ticker.non-unique");
				}
			}
			{
				if (strategy.getDraftMode() != null && !strategy.getDraftMode()) {
					Double tacticsCount = this.repository.countTacticsOfStrategy(strategy.getId());
					boolean hasParts = tacticsCount != null && tacticsCount > 0;

					super.state(context, hasParts, "draftMode", "acme.validation.strategy.parts.message");
				}
			}
			{
				Date now = MomentHelper.getBaseMoment();
				Date start = strategy.getStartMoment();
				Date end = strategy.getEndMoment();

				boolean validDates = start != null && end != null && !MomentHelper.isBefore(start, now) && MomentHelper.isAfter(end, start);
				boolean validPublishedInvention = strategy.getDraftMode() || validDates;

				super.state(context, validPublishedInvention, "*", "acme.validation.invention.dates.message");
			}
			{
				Double monthsActive = strategy.getMonthsActive();
				boolean validMonths = Boolean.TRUE.equals(strategy.getDraftMode()) || monthsActive >= 0.0;

				super.state(context, validMonths, "monthsActive", "acme.validation.invention.monthsActive.message");
			}
		}

		return !super.hasErrors(context);

	}
}
