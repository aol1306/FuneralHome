package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GraveDigger extends Employee {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private LocalDate allowanceCreationDate;

    @ManyToMany(
            mappedBy = "graveDiggers"
    )
    private List<Funeral> funerals = new ArrayList<>();

    public void addFuneral(Funeral funeral) {
        if (!funerals.contains(funeral)) {
            funerals.add(funeral);
            funeral.addGraveDigger(this);
        }
    }

    public void removeFuneral(Funeral funeral) {
        if (funerals.contains(funeral)) {
            funerals.remove(funeral);
            funeral.removeGraveDigger(this);
        }
    }

    public List<Funeral> getFunerals() {
        return funerals;
    }

    public LocalDate getAllowanceCreationDate() {
        return allowanceCreationDate;
    }

    public void setAllowanceCreationDate(LocalDate allowanceCreationDate) {
        this.allowanceCreationDate = allowanceCreationDate;
    }

    /**
     * Check grave digger's availability
     *
     * @param localDate date
     * @return true if is available, else false
     */
    public boolean isAvailable(LocalDate localDate) {
        for (var funeral : funerals) {
            if (funeral.getFuneralDate().compareTo(localDate) == 0) return false;
        }
        return true;
    }
}