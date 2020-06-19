package model;

import javax.persistence.*;

@Entity
public class Decoration {
    @Id
    @GeneratedValue
    private Long id;

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
}
