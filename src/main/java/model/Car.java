package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Entity
public abstract class Car {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String brand;
    private String model;
    private LocalDate technicalExaminationExpiry;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getTechnicalExaminationExpiry() {
        return technicalExaminationExpiry;
    }

    public void setTechnicalExaminationExpiry(LocalDate technicalExaminationExpiry) {
        this.technicalExaminationExpiry = technicalExaminationExpiry;
    }
}
