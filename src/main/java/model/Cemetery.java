package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Entity
public class Cemetery {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
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

    @Transient
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

    /**
     * Get all quarters, that are not associated with a coffin.
     * @return list of available quarters
     */
    public List<Quarter> getAvailableQuarters() {
        var all = getQuarters();
        return all.stream().filter(q -> q.getCoffin() == null).collect(Collectors.toList());
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
