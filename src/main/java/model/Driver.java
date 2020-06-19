package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Driver extends Employee {
    @Id
    @GeneratedValue
    private Long id;

    private String licenseCategory;
    private LocalDate licenseValidTo;
}
