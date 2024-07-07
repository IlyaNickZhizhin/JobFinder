package xyz.zhizhin.jobfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import xyz.zhizhin.jobfinder.dictionariesHHapi.*;

@Entity(name = "strategy")
@Data
public class FindStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="key_phrase")
    private String phrase;

    @Column(length = 50)
    private Currency currency;

    @Column(length = 50)
    private Employment employment;

    @Column(length = 50)
    private Experience experience;

    @Column(length = 50)
    private Schedule schedule;

    @Column(length = 50)
    private SearchField searchField;

    @Column(length = 50)
    private VacancySearchOrder vacancySearchOrder;

    @Column(length = 50)
    private long salary;

    @Column(name = "onlyWithSalaryStatus")
    private boolean onlyWithSalaryStatus;

    @Column(name = "period")
    private int period;

}
