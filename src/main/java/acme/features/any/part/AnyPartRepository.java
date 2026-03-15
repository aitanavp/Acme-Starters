
package acme.features.any.part;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Part;

@Repository
public interface AnyPartRepository extends AbstractRepository {

	@Query("select p from Part p where p.invention.id = :inventionId and p.invention.draftMode = false")
	public List<Part> findPublishedPartsByInventionId(int inventionId);

	@Query("select p from Part p where p.id = :partId")
	public Part findPartById(int partId);
}
