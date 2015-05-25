package org.vtsukur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.vtsukur.rest.core.domain.Booking;
import org.vtsukur.rest.core.domain.BookingRepository;

/**
 * @author volodymyr.tsukur
 */
@RestController
@RequestMapping("/crud/bookings")
public class BookingsCrudController {

    @Autowired
    private BookingRepository bookingRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Booking> getBookings(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "25") int size) {
        return bookingRepository.findAll(new PageRequest(page, size));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Booking getBooking(@PathVariable Long id) {
        return bookingRepository.findOne(id);
    }

}
