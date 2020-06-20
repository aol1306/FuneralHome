package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;
    private String phoneNumber;

    @OneToOne
    private Funeral funeral;

    public Funeral getFuneral() {
        return funeral;
    }

    public void setFuneral(Funeral funeral) {
        if (this.funeral == funeral) return;
        if (this.funeral != null && funeral != null) {
            this.funeral.setCustomer(null);
        }
        this.funeral = funeral;
        if (funeral != null) {
            funeral.setCustomer(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
