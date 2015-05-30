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

    private Integer likes;

    @Embedded
    private Rating rating;

    @Embedded
    private Address address;

    @OneToMany
    private final Set<Room> rooms = new HashSet<>();

}
