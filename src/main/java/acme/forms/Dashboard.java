
package acme.forms;

import acme.client.components.basis.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard extends AbstractForm {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Double						totalNumberOfProjects;
	Double						deviationOfProjects;
	Double						minEffort;
	Double						maxEffort;
	Double						averageEffort;
	Double						deviationEffort;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
