package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee responsible for digging grave, in which the coffin is put. There needs to be at least one
 * grave digger on a funeral.
 */
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

    /**
     * Adds funeral association, and creates a reverse association if needed.
     * @param funeral funeral
     */
    public void addFuneral(Funeral funeral) {
        if (!funerals.contains(funeral)) {
            funerals.add(funeral);
            funeral.addGraveDigger(this);
        }
    }

    /**
     * Removes an association with a funeral. Removes a reverse association if needed.
     * @param funeral funeral
     */
    public void removeFuneral(Funeral funeral) {
        if (funerals.contains(funeral)) {
            funerals.remove(funeral);
            funeral.removeGraveDigger(this);
        }
    }

    /**
     * Returns a list of funerals, at which this grave digger worked.
     * @return list of funerals
     */
    public List<Funeral> getFunerals() {
        return funerals;
    }

    /**
     * Returns a date, on which grave digger's allowance was granted.
     * @return date
     */
    public LocalDate getAllowanceCreationDate() {
        return allowanceCreationDate;
    }

    /**
     * Sets grave digger's allowance creation date.
     * @param allowanceCreationDate date
     */
    public void setAllowanceCreationDate(LocalDate allowanceCreationDate) {
        this.allowanceCreationDate = allowanceCreationDate;
    }

    /**
     * Check grave digger's availability
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