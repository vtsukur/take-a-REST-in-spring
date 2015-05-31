package org.vtsukur.rest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.vtsukur.rest.core.domain.Booking;

import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.vtsukur.rest.MapBasedHalRepresentations.linkPresent;
import static org.vtsukur.rest.MapBasedRepresentations.propertyMatches;

/**
 * @author volodymyr.tsukur
 */
public final class MapBasedBookingHalRepresentationMatcher extends TypeSafeMatcher<Map<String, ?>> {

    private final Booking reference;

    private MapBasedBookingHalRepresentationMatcher(final Booking reference) {
        this.reference = reference;
    }

    @Override
    protected boolean matchesSafely(final Map<String, ?> item) {
        boolean propertiesMatch =
                propertyMatches(item, "checkIn", reference.getCheckIn().format(DateTimeFormatter.ISO_DATE)) &&
                propertyMatches(item, "checkOut", reference.getCheckOut().format(DateTimeFormatter.ISO_DATE)) &&
                propertyMatches(item, "status", reference.getStatus().name()) &&
                propertyMatches(item, "price", JacksonModulesConfiguration.MoneyModule.format(reference.getPrice()));
        boolean paymentLinkCheck = (reference.getStatus() != Booking.Status.CREATED || linkPresent(item, "payment"));
        boolean updateLinkCheck = (reference.getStatus() != Booking.Status.CREATED || linkPresent(item, "update"));
        boolean cancelLinkCheck = (reference.getStatus() != Booking.Status.CREATED || linkPresent(item, "cancel"));
        return propertiesMatch && paymentLinkCheck && updateLinkCheck && cancelLinkCheck;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(reference.toString());
    }

    public static MapBasedBookingHalRepresentationMatcher isBooking(final Booking booking) {
        return new MapBasedBookingHalRepresentationMatcher(booking);
    }

}
