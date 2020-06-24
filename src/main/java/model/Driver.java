package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Driver extends Employee {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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

    public String getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(String licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public LocalDate getLicenseValidTo() {
        return licenseValidTo;
    }

    public void setLicenseValidTo(LocalDate licenseValidTo) {
        this.licenseValidTo = licenseValidTo;
    }
}
