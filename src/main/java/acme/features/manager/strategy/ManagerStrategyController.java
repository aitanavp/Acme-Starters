
package acme.features.manager.strategy;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.strategies.Strategy;
import acme.realms.Manager;

@Controller
public class ManagerStrategyController extends AbstractController<Manager, Strategy> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ManagerStrategyListService.class);
		super.addBasicCommand("show", ManagerStrategyShowService.class);
		//super.addBasicCommand("create", ManagerInventionCreateService.class);
		//super.addBasicCommand("update", ManagerInventionUpdateService.class);
		//super.addBasicCommand("delete", ManagerInventionDeleteService.class);

		//super.addCustomCommand("publish", "update", ManagerInventionPublishService.class);
	}

}
