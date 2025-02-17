package xyz.zhizhin.jobfinder.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class HeadHunterUser {

    @Id
    @Column(name = "telegramId")
    private Long Id;

    @Column(name = "telegramName")
    private String telegramName;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "middleName")
    private String middleName;

    @Column(name = "gender")
    private String gender;

    @Column
    private int age;

    @Column
    private String additionalText;

    @Column(name ="expirience")
    private int expirience;

    @Column(name = "skills")
    private String setSkills;

    @OneToOne
    @JoinColumn(name ="strategy_id")
    private FindStrategy findStrategy;

    @ManyToMany
    @JoinTable(name = "matches",
            joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id"))
    private Set<HeadHunterVacancy> vacancies;

}
