package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;
    private LocalDate dateOfEmployment;
}
