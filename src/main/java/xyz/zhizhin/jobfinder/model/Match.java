package xyz.zhizhin.jobfinder.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "matches")
@Data
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long matchId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "vacancy_id")
    private long vacancyId;

    @Column(name = "request")
    private boolean userResponseStatus;

    @Column(name = "responce")
    private boolean companyResponseStatus;

    @Column
    private boolean match;
}
