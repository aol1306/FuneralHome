package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Driver is an employee that transports coffins to funeral place, using a caravan as transport method.
 */
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

    /**
     * Sets a caravan that is associated with the driver. Creates reverse connection when needed.
     * @param caravan caravan
     */
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

    /**
     * Returns driver's driving license category.
     * @return driving license category
     */
    public String getLicenseCategory() {
        return licenseCategory;
    }

    /**
     * Sets driver's driving license category.
     * @param licenseCategory driving license category.
     */
    public void setLicenseCategory(String licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    /**
     * Returns the date of validity of driving license.
     * @return date
     */
    public LocalDate getLicenseValidTo() {
        return licenseValidTo;
    }

    /**
     * Sets the date of validity of the driving license.
     * @param licenseValidTo date
     */
    public void setLicenseValidTo(LocalDate licenseValidTo) {
        this.licenseValidTo = licenseValidTo;
    }
}
