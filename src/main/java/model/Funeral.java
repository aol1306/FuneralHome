package model;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Funeral {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate orderDate;
    private LocalDate funeralDate;
    private String clientData;
    private Duration duration;
    @Enumerated(EnumType.STRING)
    private FuneralStatus status;

    @ManyToMany
    private List<GraveDigger> graveDiggers = new ArrayList<>();
    @OneToMany
    private List<Coffin> coffins = new ArrayList<>();
    @ManyToMany
    private List<Caravan> caravans = new ArrayList<>();

    public void addGraveDigger(GraveDigger graveDigger) {
        if(!graveDiggers.contains(graveDigger)) {
            graveDiggers.add(graveDigger);
            graveDigger.addFuneral(this);
        }
    }

    public void removeGraveDigger(GraveDigger graveDigger) {
        if(graveDiggers.contains(graveDigger)) {
            graveDiggers.remove(graveDigger);
            graveDigger.removeFuneral(this);
        }
    }

    public void addCoffin(Coffin coffin) {
        if(!coffins.contains(coffin)) {
            coffins.add(coffin);
            coffin.setFuneral(this);
        }
    }

    public void removeCoffin(Coffin coffin) {
        if(coffins.contains(coffin)) {
            coffins.remove(coffin);
            coffin.setFuneral(null);
        }
    }

    public void addCaravan(Caravan caravan) {
        if(!caravans.contains(caravan)) {
            caravans.add(caravan);
            caravan.addFuneral(this);
        }
    }

    public void removeCaravan(Caravan caravan) {
        if(caravans.contains(caravan)) {
            caravans.add(caravan);
            caravan.removeFuneral(this);
        }
    }

    public List<GraveDigger> getGraveDiggers() {
        return graveDiggers;
    }

    public List<Coffin> getCoffins() {
        return coffins;
    }

    public List<Caravan> getCaravans() {
        return caravans;
    }
}
