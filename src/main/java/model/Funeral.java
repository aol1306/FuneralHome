package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    public Customer getCustomer() {
        return customer;
    }

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

    public LocalDate getFuneralDate() {
        return funeralDate;
    }

    public void setFuneralDate(LocalDate funeralDate) {
        this.funeralDate = funeralDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getClientData() {
        return clientData;
    }

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public FuneralStatus getStatus() {
        return status;
    }

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
