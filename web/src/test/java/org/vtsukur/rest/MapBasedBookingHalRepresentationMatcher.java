package org.vtsukur.rest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.vtsukur.rest.core.domain.Booking;

import java.util.Map;

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
        return true;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(reference.toString());
    }

    public static MapBasedBookingHalRepresentationMatcher isBooking(final Booking booking) {
        return new MapBasedBookingHalRepresentationMatcher(booking);
    }

}
