
package acme.features.any.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventors.Inventor;

@Repository
public interface AnyInventorRepository extends AbstractRepository {

	@Query("select i from Inventor i where i.id = :inventorId")
	public Inventor findInventorById(int inventorId);

}
