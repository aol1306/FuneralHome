package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PremiumCoffin extends Coffin {
    @Id
    @GeneratedValue
    private Long id;

    private String material;

    private List<Decoration> decorations = new ArrayList<>();

    public void addDecoration(Decoration decoration) {
        if (!decorations.contains(decoration)) {
            decorations.add(decoration);
            decoration.setPremiumCoffin(this);
        }
    }

    public void removeDecoration(Decoration decoration) {
        if (decorations.contains(decoration)) {
            decorations.remove(decoration);
            decoration.setPremiumCoffin(null);
        }
    }

    public List<Decoration> getDecorations() {
        return decorations;
    }
}
