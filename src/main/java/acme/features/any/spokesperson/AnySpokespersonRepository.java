
package acme.features.any.spokesperson;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.spokespersons.Spokesperson;

@Repository
public interface AnySpokespersonRepository extends AbstractRepository {

	@Query("select s from Spokesperson s where s.id = :spokespersonId")
	public Spokesperson findSpokespersonById(int spokespersonId);

}
