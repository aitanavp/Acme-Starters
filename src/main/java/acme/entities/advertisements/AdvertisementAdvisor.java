
package acme.entities.advertisements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.client.helpers.LoggerHelper;

@ControllerAdvice
public class AdvertisementAdvisor {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdvertisementRepository repository;

	// Beans ------------------------------------------------------------------


	@ModelAttribute("advertisement")
	public Advertisement getAdvertisement() {
		Advertisement result;

		try {
			result = this.repository.findRandomAdvertisement();
		} catch (final Throwable oops) {
			LoggerHelper.error(oops.getLocalizedMessage());
			result = null;
		}

		return result;
	}
}
