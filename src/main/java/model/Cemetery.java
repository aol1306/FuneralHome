package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Cemetery {
    @Id
    @GeneratedValue
    private Long id;

    private String address;
    private Long distanceFromFuneralHome;
}
