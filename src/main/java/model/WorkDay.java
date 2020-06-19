package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class WorkDay {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
