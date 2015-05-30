package org.vtsukur.rest.styles.hypermedia;

import org.javamoney.moneta.Money;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import org.vtsukur.rest.core.domain.Booking;
import org.vtsukur.rest.etc.money.Currencies;

import java.time.Period;

/**
 * @author volodymyr.tsukur
 */
@Component
@RepositoryEventHandler
public class BookingEventHandler {

    @HandleBeforeCreate
    @HandleBeforeSave
    public void onCreateOrSave(final Booking booking) {
        if (booking.getStatus() == null) {
            booking.setStatus(Booking.Status.CREATED);
        }

        if (booking.getStatus() == Booking.Status.CREATED) {
            booking.setPrice(
                    Money.of(Period.between(booking.getCheckIn(), booking.getCheckOut()).getDays(), Currencies.USD).
                            multiply(booking.getRoom().getPrice().getNumber()));
        }
    }

}
