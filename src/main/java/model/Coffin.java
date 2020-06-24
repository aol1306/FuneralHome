package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Abstract class, which children represent premium and standard coffins.
 */
@Entity
public abstract class Coffin {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private Double price;
    private String deadmanName;
    private String deadmanSurname;

    @ManyToOne
    private Funeral funeral;

    @OneToOne(mappedBy = "coffin")
    private Quarter quarter;

    /**
     * Sets funeral associated with this coffin.
     * @param funeral funeral
     */
    public void setFuneral(Funeral funeral) {
        if (this.funeral == funeral) return;
        if (this.funeral != null) {
            this.funeral.removeCoffin(this);
        }
        this.funeral = funeral;
        if (funeral != null) {
            funeral.addCoffin(this);
        }
    }

    /**
     * Returns funeral associated with this coffin.
     * @return funeral
     */
    public Funeral getFuneral() {
        return funeral;
    }

    /**
     * Returns quarter associated with this coffin.
     * @return funeral
     */
    public Quarter getQuarter() {
        return quarter;
    }

    /**
     * Sets quarter associated with this coffin. Creates reverse connection if needed.
     * @param quarter quarter
     */
    public void setQuarter(Quarter quarter) {
        if (this.quarter == quarter) return;
        if (this.quarter != null && quarter != null) {
            this.quarter.setCoffin(null);
        }
        this.quarter = quarter;
        if (quarter != null) {
            quarter.setCoffin(this);
        }
    }

    /**
     * Returns the name of a person that is supposed to lay in this coffin.
     * @return
     */
    public String getDeadmanName() {
        return deadmanName;
    }

    /**
     * Sets the name of a person that is supposed to lay in this coffin.
     * @param deadmanName name
     */
    public void setDeadmanName(String deadmanName) {
        this.deadmanName = deadmanName;
    }

    /**
     * Returns the surname of a person that is supposed to lay in this coffin.
     * @return surname
     */
    public String getDeadmanSurname() {
        return deadmanSurname;
    }

    /**
     * Sets the surname of a person that is supposed to lay in this coffin.
     * @param deadmanSurname surname
     */
    public void setDeadmanSurname(String deadmanSurname) {
        this.deadmanSurname = deadmanSurname;
    }

    /**
     * Returns coffin type as string. Method should be overridden by inheriting classes.
     * @return type string
     */
    public String getType() {
        return "generic";
    }

    /**
     * Returns coffin price.
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets coffin price.
     * @param price price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns total price of the coffin. This method should be overridden if price calculation method changes in children classes.
     * @return total coffin price
     */
    public Double getTotalPrice() {
        return this.getPrice();
    }
}
