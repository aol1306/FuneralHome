package model;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Entity
public class Funeral {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate orderDate;
    private LocalDate funeralDate;
    // TODO to external class?
    private String clientData;
    private Duration duration;
    @Enumerated(EnumType.STRING)
    private FuneralStatus status;
}
