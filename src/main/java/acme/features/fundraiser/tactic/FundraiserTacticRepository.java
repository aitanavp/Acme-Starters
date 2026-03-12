
package acme.features.fundraiser.tactic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface FundraiserTacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	List<Tactic> findTacticsByStrategyId(int strategyId);

	@Query("select t from Tactic t where t.id = :tacticId")
	Tactic findTacticById(int tacticId);

	@Query("select s from Strategy s where s.id = :strategyId")
	Strategy findStrategyById(int strategyId);

}
