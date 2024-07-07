package xyz.zhizhin.jobfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.zhizhin.jobfinder.model.HeadHunterUser;

public interface HeadHunterUserRepository extends JpaRepository<HeadHunterUser, Long> {
}
