package xyz.zhizhin.jobfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.zhizhin.jobfinder.model.FindStrategy;

public interface FindStrategyRepository extends JpaRepository<FindStrategy, Integer> {
}
