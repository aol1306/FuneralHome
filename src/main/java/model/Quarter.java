package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Quarter {
    @Id
    @GeneratedValue
    private Long id;

    private String number;
    private LocalDate paidUntil;

    @OneToOne(
            mappedBy = "quarter"
    )
    private Coffin coffin;
    @ManyToOne
    @JoinColumn(name = "cemetery_id")
    private Cemetery cemetery;

    // default constructor for Hibernate
    private Quarter() {}

    private Quarter(String number) {
        this.number = number;
    }

    public static Quarter createQuarter(Cemetery cemetery, String number) throws Exception {
        if (cemetery == null) {
            throw new Exception("Cemetery does not exist");
        }
        Quarter quarter = new Quarter(number);
        cemetery.addQuarter(quarter);
        return quarter;
    }

    public Coffin getCoffin() {
        return coffin;
    }

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

    public Cemetery getCemetery() {
        return cemetery;
    }

    // for hibernate - get set
    private void setCemetery(Cemetery cemetery) {
        this.cemetery = cemetery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getPaidUntil() {
        return paidUntil;
    }

    public void setPaidUntil(LocalDate paidUntil) {
        this.paidUntil = paidUntil;
    }
}
