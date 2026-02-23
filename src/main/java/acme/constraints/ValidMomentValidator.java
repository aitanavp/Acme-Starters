
package acme.constraints;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.client.helpers.MomentHelper;

public class ValidMomentValidator implements ConstraintValidator<ValidMoment, LocalDateTime> {

	@Override
	public boolean isValid(final LocalDateTime value, final ConstraintValidatorContext context) {
		if (value == null)
			return true;

		Date nowDate = MomentHelper.getCurrentMoment();
		LocalDateTime now = LocalDateTime.ofInstant(nowDate.toInstant(), ZoneId.systemDefault());

		return value.isAfter(now);
	}
}
