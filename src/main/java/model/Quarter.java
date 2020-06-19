package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Quarter {
    @Id
    @GeneratedValue
    private Long id;

    private String number;
    private LocalDate paidUntil;
}
