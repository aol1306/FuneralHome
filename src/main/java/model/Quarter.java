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
}
