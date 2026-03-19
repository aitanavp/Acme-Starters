
package acme.entities.inventions;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface InventionRepository extends AbstractRepository {

	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :inventionId")
	Double computeInventionCost(int inventionId);

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	Long countPartsByInventionId(int inventionId);

	@Query("select i from Invention i where i.ticker=:ticker")
	Invention findInventionByTicker(final String ticker);

	@Query("select p from Part p where p.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);
}
