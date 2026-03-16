
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

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	int countByStrategyId(int strategyId);

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double sumExpectedPercentageByStrategyId(int strategyId);

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId and t.id != :tacticId")
	Double sumExpectedPercentageByStrategyIdExcludingTactic(int strategyId, int tacticId);
}
