package xyz.zhizhin.jobfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.zhizhin.jobfinder.model.HeadHunterVacancy;

@Repository
public interface HeadHunterVacancyRepository extends JpaRepository<HeadHunterVacancy, Long>{
}
