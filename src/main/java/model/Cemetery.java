package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.*;

@Entity
public class Cemetery {
    @Id
    @GeneratedValue
    private Long id;

    private String address;
    private Long distanceFromFuneralHome;

    private List<Quarter> quarters = new ArrayList<>();
    private static Set<Quarter> allQuarters = new HashSet<>();

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
}
