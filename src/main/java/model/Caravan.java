package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Caravan {
    @Id
    @GeneratedValue
    private Long id;

    private Integer capacity;

    private List<Funeral> funerals = new ArrayList<>();

    public void addFuneral(Funeral funeral) {
        if(!funerals.contains(funeral)) {
            funerals.add(funeral);
            funeral.addCaravan(this);
        }
    }

    public void removeFuneral(Funeral funeral) {
        if(funerals.contains(funeral)) {
            funerals.remove(funeral);
            funeral.removeCaravan(this);
        }
    }

    public List<Funeral> getFunerals() {
        return funerals;
    }
}
