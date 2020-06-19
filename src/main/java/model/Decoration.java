package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Decoration {
    @Id
    @GeneratedValue
    private Long id;

    private String material;
    private Double price;
}
