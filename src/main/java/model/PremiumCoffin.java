package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Premium coffin is a coffin that can be decorated with additional decorations. Premium coffins can never be burned.
 */
@Entity
public class PremiumCoffin extends Coffin {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String material;

    @OneToMany(
            mappedBy = "premiumCoffin",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Decoration> decorations = new ArrayList<>();

    /**
     * Adds a decoration to a coffin.
     * @param decoration decoration
     */
    public void addDecoration(Decoration decoration) {
        if (!decorations.contains(decoration)) {
            decorations.add(decoration);
            decoration.setPremiumCoffin(this);
        }
    }

    /**
     * Removes a decoration from a coffin.
     * @param decoration decoration
     */
    public void removeDecoration(Decoration decoration) {
        if (decorations.contains(decoration)) {
            decorations.remove(decoration);
            decoration.setPremiumCoffin(null);
        }
    }

    /**
     * Returns a list of decorations associated with this coffin.
     * @return list of decorations
     */
    public List<Decoration> getDecorations() {
        return decorations;
    }

    /**
     * @return type of coffin string
     */
    @Override
    public String getType() {
        return "premium";
    }

    /**
     * Returns material, of which this coffin is made.
     * @return material as string
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Sets coffin base material, for example the type of wood.
     * @param material material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Returns calculated price of this coffin and all its decorations.
     * @return price
     */
    @Override
    public Double getTotalPrice() {
        var ret = this.getPrice();
        for (var decoration : getDecorations()) {
            ret += decoration.getPrice();
        }
        return ret;
    }
}
