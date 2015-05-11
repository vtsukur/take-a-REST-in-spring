package org.vtsukur.rest.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Getter
@Setter
@ToString(exclude = "hotel")
public class Room extends BaseEntity {

    @ManyToOne
    private Hotel hotel;

    private String type;

    private Money price;

    private Room() {}

    public Room(final Hotel hotel, final String type) {
        this.hotel = hotel;
        this.type = type;
    }

}
