package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

/**
 * Car is a general class for Caravans and Delivery cars.
 */
@Entity
public abstract class Car {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String brand;
    private String model;
    private LocalDate technicalExaminationExpiry;

    /**
     * Returns car brand
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets car brand
     * @param brand car brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns car model
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets car model
     * @param model car model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns technical examination expiry date
     * @return expiry date
     */
    public LocalDate getTechnicalExaminationExpiry() {
        return technicalExaminationExpiry;
    }

    /**
     * Sets technical examination expiry date
     * @param technicalExaminationExpiry expiry date
     */
    public void setTechnicalExaminationExpiry(LocalDate technicalExaminationExpiry) {
        this.technicalExaminationExpiry = technicalExaminationExpiry;
    }
}
