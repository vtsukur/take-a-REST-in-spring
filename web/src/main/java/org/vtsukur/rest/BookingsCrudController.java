package org.vtsukur.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.vtsukur.rest.core.domain.Booking;
import org.vtsukur.rest.core.domain.BookingRepository;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.core.domain.HotelRepository;
import org.vtsukur.rest.crud.BookingSaveRequest;

/**
 * @author volodymyr.tsukur
 */
@RestController
@RequestMapping("/crud/bookings")
public class BookingsCrudController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> post(@RequestBody BookingSaveRequest request) {
        Hotel hotel = hotelRepository.findOne(request.getHotelId());
        Booking savedBooking = bookingRepository.save(new Booking(request.getCheckIn(), request.getCheckOut(), hotel));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(MvcUriComponentsBuilder.fromMethodName(BookingsCrudController.class, "getOne", savedBooking.getId()).build().toUri());
        return new ResponseEntity<>(savedBooking, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PATCH, consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> merge(@PathVariable Long id, @RequestBody BookingSaveRequest request) {
        Booking booking = bookingRepository.findOne(id);
        Hotel hotel = hotelRepository.findOne(request.getHotelId());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        booking.setHotel(hotel);
        Booking savedBooking = bookingRepository.save(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Booking> getPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "25") int size) {
        return bookingRepository.findAll(new PageRequest(page, size));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Booking getOne(@PathVariable Long id) {
        return bookingRepository.findOne(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable Long id) {
        bookingRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
