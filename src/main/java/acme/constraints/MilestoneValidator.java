
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.campaigns.Milestone;

@Validator
public class MilestoneValidator extends AbstractValidator<ValidMilestone, Milestone> {

	@Override
	protected void initialise(final ValidMilestone annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Milestone milestone, final ConstraintValidatorContext context) {

		assert context != null;

		if (milestone == null)
			return true;

		return !super.hasErrors(context);
	}
}
