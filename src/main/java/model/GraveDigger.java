package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GraveDigger extends Employee {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate allowanceCreationDate;

    @ManyToMany(
            mappedBy = "graveDiggers"
    )
    private List<Funeral> funerals = new ArrayList<>();

    public void addFuneral(Funeral funeral) {
        if(!funerals.contains(funeral)) {
            funerals.add(funeral);
            funeral.addGraveDigger(this);
        }
    }

    public void removeFuneral(Funeral funeral) {
        if(funerals.contains(funeral)) {
            funerals.remove(funeral);
            funeral.removeGraveDigger(this);
        }
    }

    public List<Funeral> getFunerals() {
        return funerals;
    }
}
