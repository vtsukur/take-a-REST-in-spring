package org.vtsukur.rest.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private LocalDate checkIn;

    @Lob
    private LocalDate checkOut;

    @Lob
    private Money price;

    @ManyToOne
    private Hotel hotel;

    @JsonIgnore
    private Status status;

    public Booking(LocalDate checkIn, LocalDate checkOut, Hotel hotel) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.hotel = hotel;
        this.status = Status.CREATED;
    }

    public enum Status {

        CREATED,

        PAID,

        CANCELLED,

        SERVED

    }

}
