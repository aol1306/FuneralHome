package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Caravan {
    @Id
    @GeneratedValue
    private Long id;

    private Integer capacity;

    @ManyToMany(
            mappedBy = "caravans"
    )
    private List<Funeral> funerals = new ArrayList<>();

    @OneToOne(
            mappedBy = "caravan"
    )
    private Driver driver;

    public void addFuneral(Funeral funeral) {
        if(!funerals.contains(funeral)) {
            funerals.add(funeral);
            funeral.addCaravan(this);
        }
    }

    public void removeFuneral(Funeral funeral) {
        if(funerals.contains(funeral)) {
            funerals.remove(funeral);
            funeral.removeCaravan(this);
        }
    }

    public List<Funeral> getFunerals() {
        return funerals;
    }

    public void setDriver(Driver driver) {
        if (this.driver == driver) return;
        if (this.driver != null && driver != null) {
            this.driver.setCaravan(null);
        }
        this.driver = driver;
        if (driver != null) {
            driver.setCaravan(this);
        }
    }

    public Driver getDriver() {
        return driver;
    }
}
