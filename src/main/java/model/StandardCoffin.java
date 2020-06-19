package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StandardCoffin extends Coffin {
    @Id
    @GeneratedValue
    private Long id;

    private boolean isBurnable;
}
