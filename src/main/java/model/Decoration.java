package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Decoration {
    @Id
    @GeneratedValue
    private Long id;

    private String material;
    private Double price;

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
