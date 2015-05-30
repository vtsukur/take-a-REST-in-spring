package org.vtsukur.rest.core.domain;

import lombok.*;

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
@NoArgsConstructor
@AllArgsConstructor
public class Hotel extends BaseEntity {

    private String name;

    private PropertyType propertyType;

    private Integer likes;

    @Embedded
    private Rating rating;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "hotel")
    private final Set<Room> rooms = new HashSet<>();

    public enum PropertyType {

        HOTEL

    }

}
