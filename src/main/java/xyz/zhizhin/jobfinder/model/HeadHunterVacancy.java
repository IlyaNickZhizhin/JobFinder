package xyz.zhizhin.jobfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Currency;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Employment;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Experience;
import xyz.zhizhin.jobfinder.dictionariesHHapi.Schedule;

import java.util.Set;

@Entity
@Data
public class HeadHunterVacancy {

    @Id
    private Long id;

    @Column(name = "company")
    private String companyName;

    @Column(name = "city")
    private String city;

    @Column
    private Currency currency;

    @Column
    private Integer salaryFrom;

    @Column
    private Integer salaryTo;

    @Column
    private Employment employment;

    @Column
    private Experience askedExperience;

    @Column
    private Schedule schedule;

    @Column(name = "supervisor")
    private String supervisor;

    @Column(name = "inn")
    private long inn;

    @ManyToMany
    @JoinTable(name = "matches",
        joinColumns = @JoinColumn(name ="vacancy_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<HeadHunterUser> users;

    private String description;

    private String vacancyUrl;

}
