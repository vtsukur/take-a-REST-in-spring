package org.vtsukur.rest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.vtsukur.rest.core.domain.Booking;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.vtsukur.rest.MapBasedRepresentations.propertyMatches;

/**
 * @author volodymyr.tsukur
 */
public final class MapBasedBookingRepresentationMatcher extends TypeSafeMatcher<Map<String, ?>> {

    private final Booking reference;

    private MapBasedBookingRepresentationMatcher(final Booking reference) {
        this.reference = reference;
    }

    @Override
    protected boolean matchesSafely(final Map<String, ?> item) {
        return propertyMatches(item, "id", reference.getId())
                && propertyMatches(item, "checkIn", fromLocalDate(reference.getCheckIn()))
                && propertyMatches(item, "checkOut", fromLocalDate(reference.getCheckOut()))
                && propertyMatches(item, "status", reference.getStatus().name())
                && propertyMatches(item, "price", JacksonModulesConfiguration.MoneyModule.format(reference.getPrice()));
    }

    private static List<Object> fromLocalDate(final LocalDate localDate) {
        return Arrays.asList(
                localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(reference.toString());
    }

    public static MapBasedBookingRepresentationMatcher isBooking(final Booking booking) {
        return new MapBasedBookingRepresentationMatcher(booking);
    }

}
