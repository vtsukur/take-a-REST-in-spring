package org.vtsukur.rest.core.domain;

import lombok.*;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity {

    @Lob
    private LocalDate checkInDate;

    @Lob
    private LocalDate checkOutDate;

    @Lob
    private Money price;

    @ManyToOne
    private Hotel hotel;

    public Booking(LocalDate checkInDate, LocalDate checkOutDate, Hotel hotel) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.hotel = hotel;
    }

}
