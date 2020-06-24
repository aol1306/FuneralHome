package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Decorations for premium coffin.
 */
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

    /**
     * Sets premium coffin associated with this decoration.
     * @param premiumCoffin premium coffin
     */
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

    /**
     * Gets the premium coffin associated with this decoration
     * @return premium coffin
     */
    public PremiumCoffin getPremiumCoffin() {
        return premiumCoffin;
    }

    /**
     * Returns decoration name.
     * @return decoration name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets decoration name.
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns material, of which the decoration is made.
     * @return material
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Sets material, of which the decoration is made.
     * @param material material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Returns decoration price.
     * @return price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets decoration price.
     * @param price price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
