package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PremiumCoffin extends Coffin {
    @Id
    @GeneratedValue
    private Long id;

    private String material;

    @OneToMany
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
