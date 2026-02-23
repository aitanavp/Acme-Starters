
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.strategies.Tactic;

@Validator
public class TacticValidator extends AbstractValidator<ValidTactic, Tactic> {

	@Override
	protected void initialise(final ValidTactic annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Tactic tactic, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (tactic == null)
			result = true;
		else
			result = !super.hasErrors(context);

		return result;
	}
}
