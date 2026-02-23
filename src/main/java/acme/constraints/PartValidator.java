
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.inventions.Part;

@Validator
public class PartValidator extends AbstractValidator<ValidPart, Part> {

	// Internal state ---------------------------------------------------------

	// ConstraintValidator interface ------------------------------------------

	@Override
	protected void initialise(final ValidPart annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Part value, final ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return false;
	}

}
