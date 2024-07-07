package xyz.zhizhin.jobfinder.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class HeadHunterVacancyResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 400)
    private String userResponse;

    private String aiResponseCorrect;

}
