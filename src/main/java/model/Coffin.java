package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    public Funeral getFuneral() {
        return funeral;
    }

    public Quarter getQuarter() {
        return quarter;
    }

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

    public String getDeadmanName() {
        return deadmanName;
    }

    public void setDeadmanName(String deadmanName) {
        this.deadmanName = deadmanName;
    }

    public String getDeadmanSurname() {
        return deadmanSurname;
    }

    public void setDeadmanSurname(String deadmanSurname) {
        this.deadmanSurname = deadmanSurname;
    }

    public String getType() {
        return "generic";
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotalPrice() {
        return this.getPrice();
    }
}
