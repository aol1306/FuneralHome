package model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cemetery {
    @Id
    @GeneratedValue
    private Long id;

    private String address;
    private Double distanceFromFuneralHome;

    @OneToMany(
            mappedBy = "cemetery",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Quarter> quarters = new ArrayList<>();
    private static Set<Quarter> allQuarters = new HashSet<>();

    // default constructor for Hibernate
    private Cemetery() {}

    public Cemetery(String address, Double distanceFromFuneralHome) {
        setAddress(address);
        setDistanceFromFuneralHome(distanceFromFuneralHome);
    }

    public void addQuarter(Quarter quarter) throws Exception {
        if (!quarters.contains(quarter)) {
            if (allQuarters.contains(quarter)) {
                throw new Exception("This quarter is already used with another cemetery!");
            }
            quarters.add(quarter);
            allQuarters.add(quarter);
        }
    }

    public List<Quarter> getQuarters() {
        return quarters;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getDistanceFromFuneralHome() {
        return distanceFromFuneralHome;
    }

    public void setDistanceFromFuneralHome(Double distanceFromFuneralHome) {
        this.distanceFromFuneralHome = distanceFromFuneralHome;
    }

    @Override
    public String toString() {
        return getAddress();
    }
}
