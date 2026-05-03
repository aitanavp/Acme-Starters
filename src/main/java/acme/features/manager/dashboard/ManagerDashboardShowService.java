/*
 * ManagerDashboardShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.forms.Dashboard;

@Service
public class ManagerDashboardShowService extends AbstractService<Administrator, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository	repository;

	private Dashboard					dashboard;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		Double totalNumberOfProjects;
		Double deviationOfProjects;
		Double minEffort;
		Double maxEffort;
		Double averageEffort;
		Double deviationEffort;

		totalNumberOfProjects = this.repository.totalNumberOfProjects(managerId);
		deviationOfProjects = this.repository.deviationOfProjects(managerId);
		minEffort = this.repository.minEffort(managerId);
		maxEffort = this.repository.maxEffort(managerId);
		averageEffort = this.repository.averageEffort(managerId);
		deviationEffort = this.repository.deviationEffort(managerId);

		this.dashboard = super.newObject(Dashboard.class);
		this.dashboard.setTotalNumberOfProjects(totalNumberOfProjects);
		this.dashboard.setDeviationOfProjects(deviationOfProjects);
		this.dashboard.setMinEffort(minEffort);
		this.dashboard.setMaxEffort(maxEffort);
		this.dashboard.setAverageEffort(averageEffort);
		this.dashboard.setDeviationEffort(deviationEffort);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.dashboard, //
			"totalNumberOfProjects", "deviationOfProjects", // 
			"minEffort", "maxEffort", //
			"averageEffort", "deviationEffort");
	}

}
