package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Represents a cemetery, with associated Quarters.
 */
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

    /**
     * Default constructor used by Hibernate
     */
    private Cemetery() {}

    /**
     * Creates Cemetery object setting basic attributes.
     * @param address Cemetery address
     * @param distanceFromFuneralHome distance from funeral home
     */
    public Cemetery(String address, Double distanceFromFuneralHome) {
        setAddress(address);
        setDistanceFromFuneralHome(distanceFromFuneralHome);
    }

    /**
     * Associates a quarter with this cemetery.
     * @param quarter quarter
     * @throws Exception when quarter is already associated with another cemetery
     */
    public void addQuarter(Quarter quarter) throws Exception {
        if (!quarters.contains(quarter)) {
            if (allQuarters.contains(quarter)) {
                throw new Exception("This quarter is already used with another cemetery!");
            }
            quarters.add(quarter);
            allQuarters.add(quarter);
        }
    }

    /**
     * Returns all quarters associated with this cemetery.
     * @return quarter list
     */
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

    /**
     * Returns address of this cemetery
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address for this cemetery
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns distance between this cemetery and funeral home.
     * @return distance
     */
    public Double getDistanceFromFuneralHome() {
        return distanceFromFuneralHome;
    }

    /**
     * Sets the distance between this cemetery and funeral home.
     * @param distanceFromFuneralHome distance
     */
    public void setDistanceFromFuneralHome(Double distanceFromFuneralHome) {
        this.distanceFromFuneralHome = distanceFromFuneralHome;
    }

    /**
     * @return Cemetery address
     */
    @Override
    public String toString() {
        return getAddress();
    }
}
