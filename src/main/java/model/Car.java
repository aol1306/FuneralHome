package model;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class Car {
    private String brand;
    private String model;
    private LocalDate technicalExaminationExpiry;
}
