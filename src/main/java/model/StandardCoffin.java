package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * StandardCoffin is a type of coffin that has no decorations. Optionally it can be burned, if it doesn't contain
 * any elements made of metal.
 */
@Entity
public class StandardCoffin extends Coffin {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private boolean isBurnable;

    /**
     * @return coffin type string
     */
    @Override
    public String getType() {
        return "standard";
    }

    /**
     * @return true if the coffin is burnable, else false
     */
    public boolean isBurnable() {
        return isBurnable;
    }

    /**
     * Sets burnability of this coffin
     * @param burnable is burnable
     */
    public void setBurnable(boolean burnable) {
        isBurnable = burnable;
    }
}
