package org.vtsukur.rest.core.domain;

import lombok.*;
import org.javamoney.moneta.Money;
import org.vtsukur.rest.etc.money.Currencies;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import java.time.LocalDate;
import java.time.Period;

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
    private Room room;

    private Status status;

    public enum Status {

        CREATED,

        PAID,

        CANCELLED,

        SERVED

    }

    @PrePersist
    void onPrePersist() {
        if (status == null) {
            status = Booking.Status.CREATED;
        }

        if (status == Booking.Status.CREATED) {
            price = Money.of(Period.between(checkIn, checkOut).getDays(), Currencies.USD).
                            multiply(room.getPrice().getNumber());
        }
    }

}
