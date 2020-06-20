package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Driver extends Employee {
    @Id
    @GeneratedValue
    private Long id;

    private String licenseCategory;
    private LocalDate licenseValidTo;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    private Caravan caravan;

    public void setCaravan(Caravan caravan) {
        if (this.caravan == caravan) return;
        if (this.caravan != null && caravan != null) {
            this.caravan.setDriver(null);
        }
        this.caravan = caravan;
        if (caravan != null) {
            caravan.setDriver(this);
        }
    }
}
