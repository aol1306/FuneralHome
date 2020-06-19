package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class GraveDigger extends Employee {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate allowanceCreationDate;
}
