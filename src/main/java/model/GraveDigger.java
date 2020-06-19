package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GraveDigger extends Employee {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate allowanceCreationDate;

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
