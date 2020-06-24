package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Holds data of funeral home's customer.
 */
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private String name;
    private String surname;
    private String phoneNumber;

    @OneToOne(
            mappedBy = "customer"
    )
    private Funeral funeral;

    /**
     * Gets a funeral which is associated with this customer.
     * @return funeral
     */
    public Funeral getFuneral() {
        return funeral;
    }

    /**
     * Sets a funeral for this customer. Creates a reverse connection if needed.
     * @param funeral funeral
     */
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

    /**
     * Returns customer's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets customer's name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns customer's surname
     * @return surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets customer's surname
     * @param surname surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns customer's phone number
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets customer's phone number
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
