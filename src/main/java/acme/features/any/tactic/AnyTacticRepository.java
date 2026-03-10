
package acme.features.any.tactic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Tactic;

@Repository
public interface AnyTacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	public List<Tactic> findAllTacticsByStrategyId(int strategyId);

	@Query("select t from Tactic t where t.id = :tacticId")
	public Tactic findTacticById(int tacticId);
}
