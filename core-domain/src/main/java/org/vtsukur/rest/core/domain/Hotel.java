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

    @Embedded
    private Rating rating;

    @OneToMany(mappedBy = "hotel")
    private Set<Room> rooms = new HashSet<>();

    private Hotel() {}

    public Hotel(final String name, final Rating rating) {
        this.name = name;
        this.rating = rating;
    }

}
