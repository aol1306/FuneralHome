package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Decoration {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String name;
    private String material;
    private Double price;

    @ManyToOne
    private PremiumCoffin premiumCoffin;

    public void setPremiumCoffin(PremiumCoffin premiumCoffin) {
        if (this.premiumCoffin == premiumCoffin) return;
        if (this.premiumCoffin != null) {
            this.premiumCoffin.removeDecoration(this);
        }
        this.premiumCoffin = premiumCoffin;
        if (premiumCoffin != null) {
            premiumCoffin.addDecoration(this);
        }
    }

    public PremiumCoffin getPremiumCoffin() {
        return premiumCoffin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
