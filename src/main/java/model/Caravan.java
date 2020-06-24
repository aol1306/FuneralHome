package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Caravan is a special kind of car that is used to deliver coffins to funeral place.
 */
@Entity
public class Caravan {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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

    /**
     * Adds associated funeral - enables use of the caravan during the funeral
     * @param funeral funeral
     */
    public void addFuneral(Funeral funeral) {
        if(!funerals.contains(funeral)) {
            funerals.add(funeral);
            funeral.addCaravan(this);
        }
    }

    /**
     * Removes funeral from the caravan.
     * @param funeral funeral
     */
    public void removeFuneral(Funeral funeral) {
        if(funerals.contains(funeral)) {
            funerals.remove(funeral);
            funeral.removeCaravan(this);
        }
    }

    /**
     * Returns all funerals associated with the caravan.
     * @return funeral list
     */
    public List<Funeral> getFunerals() {
        return funerals;
    }

    /**
     * Sets the car driver
     * @param driver driver
     */
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

    /**
     * Returns car driver
     * @return driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Returns caravan's capacity, measured in coffins that it is able to transport at once.
     * @return number of coffins
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Sets caravan's capacity, measured in coffins that it is able to transport at once.
     * @param capacity number of coffins
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
