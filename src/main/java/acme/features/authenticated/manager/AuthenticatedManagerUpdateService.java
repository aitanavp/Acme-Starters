
package acme.features.authenticated.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Manager;

@Service
public class AuthenticatedManagerUpdateService extends AbstractService<Authenticated, Manager> {

	@Autowired
	private AuthenticatedManagerRepository	repository;

	private Manager							manager;


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.manager = this.repository.findOneManagerByUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRealmOfType(Manager.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.manager, "position", "skills", "isExecutive");
	}

	@Override
	public void validate() {
		super.validateObject(this.manager);
	}

	@Override
	public void execute() {
		this.repository.save(this.manager);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.manager, "position", "skills", "isExecutive");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
