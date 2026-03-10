
package acme.features.any.strategy;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;

@Repository
public interface AnyStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.id = :strategyId")
	public Strategy findStrategyById(int strategyId);

	@Query("select s from Strategy s where s.draftMode = false")
	public List<Strategy> findAllPublishedStrategies();

}
