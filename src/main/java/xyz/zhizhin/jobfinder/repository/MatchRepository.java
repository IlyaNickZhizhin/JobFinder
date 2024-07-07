package xyz.zhizhin.jobfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.zhizhin.jobfinder.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
