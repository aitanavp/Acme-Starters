
package acme.features.authenticated.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.fundraisers.Fundraiser;

@Repository
public interface AuthenticatedFundraiserRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select f from Fundraiser f where f.userAccount.id = :userAccountId")
	Fundraiser findOneFundraiserByUserAccountId(int userAccountId);
}
