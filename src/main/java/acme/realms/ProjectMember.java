/*
 * ProjectMember.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.realms;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import acme.client.components.basis.AbstractRole;
import acme.client.components.basis.AbstractSquad;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectMember extends AbstractSquad {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;

	// AbstractSquad interface ------------------------------------------------


	@Mandatory
	// @Valid  // HINT: inherited from the ancestor.
	@Transient
	@Override
	public Set<Class<? extends AbstractRole>> getMembers() {
		Set<Class<? extends AbstractRole>> result;

		result = Set.of(Inventor.class, Spokesperson.class, Fundraiser.class);

		return result;
	}

}
