package model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Coffin {
    private Double price;
    private String deadmanName;
    private String deadmanSurname;

    private Funeral funeral;
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
}
