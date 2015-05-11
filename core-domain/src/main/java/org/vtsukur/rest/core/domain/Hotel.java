package org.vtsukur.rest.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Getter
@Setter
@ToString
public class Hotel extends BaseEntity {

    private String name;

    private PropertyType propertyType;

    private Integer likes;

    @Embedded
    private Rating rating;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "hotel")
    private Set<Room> rooms = new HashSet<>();

    private Hotel() {}

    public Hotel(final String name, final PropertyType propertyType, final Integer likes, final Rating rating, final Address address) {
        this.name = name;
        this.propertyType = propertyType;
        this.likes = likes;
        this.rating = rating;
        this.address = address;
    }

    /**
     * @author volodymyr.tsukur
     */
    public enum PropertyType {

        HOTEL

    }

}
