package org.vtsukur.rest.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import javax.persistence.Lob;
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
    @JsonIgnore
    private Hotel hotel;

    private String type;

    @Lob
    private Money price;

    private Room() {}

    public Room(final Hotel hotel, final String type, final Money price) {
        this.hotel = hotel;
        this.type = type;
        this.price = price;
    }

}
