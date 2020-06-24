package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a funeral in a system. Contains associations with classes like: Coffin, Customer, GraveDigger and Caravan.
 */
@Entity
@Table(name = "Funeral")
public class Funeral {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    private LocalDate orderDate;
    private LocalDate funeralDate;
    private String clientData;
    private Duration duration;
    @Enumerated(EnumType.STRING)
    private FuneralStatus status;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "Funeral_GraveDigger",
            joinColumns = @JoinColumn(name = "funeral_id"),
            inverseJoinColumns = @JoinColumn(name = "gravedigger_id")
    )
    private List<GraveDigger> graveDiggers = new ArrayList<>();
    @OneToMany(
            mappedBy = "funeral",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Coffin> coffins = new CopyOnWriteArrayList<>();
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "Funeral_Caravan",
            joinColumns = @JoinColumn(name = "funeral_id"),
            inverseJoinColumns = @JoinColumn(name = "caravan_id")
    )
    private List<Caravan> caravans = new ArrayList<>();
    @OneToOne(
            cascade = CascadeType.ALL
    )
    private Customer customer;

    /**
     * Returns a customer associated with this funeral.
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets a customer for this funeral. Creates a reverse association if necessary.
     * @param customer customer
     */
    public void setCustomer(Customer customer) {
        if (this.customer == customer) return;
        if (this.customer != null && customer != null) {
            this.customer.setFuneral(null);
        }
        this.customer = customer;
        if (customer != null) {
            customer.setFuneral(this);
        }
    }

    /**
     * Adds a grave digger for this funeral. Creates a reverse association.
     * @param graveDigger grave digger
     */
    public void addGraveDigger(GraveDigger graveDigger) {
        if(!graveDiggers.contains(graveDigger)) {
            graveDiggers.add(graveDigger);
            graveDigger.addFuneral(this);
        }
    }

    /**
     * Removes a grave digger from this funeral, as well as his association with it.
     * @param graveDigger grave digger
     */
    public void removeGraveDigger(GraveDigger graveDigger) {
        if(graveDiggers.contains(graveDigger)) {
            graveDiggers.remove(graveDigger);
            graveDigger.removeFuneral(this);
        }
    }

    /**
     * Adds a coffin to this funeral. Creates reverse association when needed.
     * @param coffin coffin
     */
    public void addCoffin(Coffin coffin) {
        if(!coffins.contains(coffin)) {
            coffins.add(coffin);
            coffin.setFuneral(this);
        }
    }

    /**
     * Removes a coffin associated with this funeral. Removes a reverse connection from the coffin.
     * @param coffin coffin
     */
    public void removeCoffin(Coffin coffin) {
        if(coffins.contains(coffin)) {
            coffins.remove(coffin);
            coffin.setFuneral(null);
        }
    }

    /**
     * Adds a caravan, that will be transporting the coffins for this funeral. Creates a reverse association if needed.
     * @param caravan caravan
     */
    public void addCaravan(Caravan caravan) {
        if(!caravans.contains(caravan)) {
            caravans.add(caravan);
            caravan.addFuneral(this);
        }
    }

    /**
     * Removes a caravan associated with this funeral. Removes a reverse association if needed.
     * @param caravan caravan
     */
    public void removeCaravan(Caravan caravan) {
        if(caravans.contains(caravan)) {
            caravans.add(caravan);
            caravan.removeFuneral(this);
        }
    }

    /**
     * Returns a list of grave diggers associated with this funeral.
     * @return list of grave diggers
     */
    public List<GraveDigger> getGraveDiggers() {
        return graveDiggers;
    }

    /**
     * Returns a list of coffins associated with this funeral.
     * @return list of coffins
     */
    public List<Coffin> getCoffins() {
        return coffins;
    }

    /**
     * Returns a list of caravans associated with this funeral.
     * @return list of caravans
     */
    public List<Caravan> getCaravans() {
        return caravans;
    }

    /**
     * Returns a date, on which the funeral takes place.
     * @return date
     */
    public LocalDate getFuneralDate() {
        return funeralDate;
    }

    /**
     * Sets a date, on which the funeral takes place.
     * @param funeralDate date
     */
    public void setFuneralDate(LocalDate funeralDate) {
        this.funeralDate = funeralDate;
    }

    /**
     * Returns date on with the order was placed.
     * @return date
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Sets date, on which the order is placed.
     * @param orderDate date
     */
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Returns client information.
     * @return client data
     */
    public String getClientData() {
        return clientData;
    }

    /**
     * Sets client information
     * @param clientData client data
     */
    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

    /**
     * Returns funeral duration.
     * @return duration
     */
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Returns funeral status.
     * @return status
     */
    public FuneralStatus getStatus() {
        return status;
    }

    /**
     * Sets funeral status
     * @param status status
     */
    public void setStatus(FuneralStatus status) {
        this.status = status;
    }

    /**
     * Marks funeral as cancelled.
     */
    public void cancelFuneral() {
        this.setStatus(FuneralStatus.ORDER_CANCELLED);
    }
}
