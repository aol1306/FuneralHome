package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Entity
public abstract class Car {
    @Id
    @GeneratedValue
    private Long id;

    private String brand;
    private String model;
    private LocalDate technicalExaminationExpiry;
}
