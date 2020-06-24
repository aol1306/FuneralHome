package model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Quarter is a place on a cemetery, in which one coffin can be buried.
 */
@Entity
public class Quarter {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String number;
    private LocalDate paidUntil;

    @OneToOne(fetch = FetchType.LAZY)
    private Coffin coffin;
    @ManyToOne
    private Cemetery cemetery;

    /**
     * Default constructor for Hibernate.
     */
    private Quarter() {}

    /**
     * Creates a quarter and sets its number.
     * @param number number
     */
    private Quarter(String number) {
        this.number = number;
    }

    /**
     * Composition constructor.
     * @param cemetery cemetery, on which the quarter is present
     * @param number quarter number on a cemetery
     * @return new Quarter
     * @throws Exception when cemetery is null
     */
    public static Quarter createQuarter(Cemetery cemetery, String number) throws Exception {
        if (cemetery == null) {
            throw new Exception("Cemetery does not exist");
        }
        Quarter quarter = new Quarter(number);
        quarter.setCemetery(cemetery);
        cemetery.addQuarter(quarter);
        return quarter;
    }

    /**
     * Returns a coffin buried at this quarter.
     * @return coffin
     */
    public Coffin getCoffin() {
        return coffin;
    }

    /**
     * Sets a coffin associated with this quarter.
     * @param coffin coffin
     */
    public void setCoffin(Coffin coffin) {
        if (this.coffin == coffin) return;
        if (this.coffin != null && coffin != null) {
            this.coffin.setQuarter(null);
        }
        this.coffin = coffin;
        if (coffin != null) {
            coffin.setQuarter(this);
        }
    }

    /**
     * Returns a cemetery, on which this quarter is located.
     * @return cemetery
     */
    public Cemetery getCemetery() {
        return cemetery;
    }

    /**
     * Sets a cemetery, on which this quarter is located
     * @param cemetery cemetery
     */
    public void setCemetery(Cemetery cemetery) {
        this.cemetery = cemetery;
    }

    /**
     * @return this quarter's number
     */
    @Override
    public String toString() {
        return number;
    }

    /**
     * Returns date until which the quarter has been paid for.
     * @return date
     */
    public LocalDate getPaidUntil() {
        return paidUntil;
    }

    /**
     * Sets a date, until which the quarter has been paid for.
     * @param paidUntil date
     */
    public void setPaidUntil(LocalDate paidUntil) {
        this.paidUntil = paidUntil;
    }
}
