package org.vtsukur.rest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.vtsukur.rest.core.domain.Booking;

import java.util.Map;

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
        return MapBasedHalRepresentations.propertyMatches(item, "id", reference.getId());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText(reference.toString());
    }

    public static MapBasedBookingRepresentationMatcher isBooking(final Booking booking) {
        return new MapBasedBookingRepresentationMatcher(booking);
    }

}
