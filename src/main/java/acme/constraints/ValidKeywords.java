package acme.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@NotBlank
@Length(min = 1, max = 255)
// Each keyword is a non-empty word; keywords are separated by ", " (comma + single space)
// e.g. "innovation, technology, research"
@Pattern(regexp = "^\\S+(, \\S+)*$")
public @interface ValidKeywords {

	// Standard validation properties -----------------------------------------

	String message() default "{acme.validation.keywords.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}