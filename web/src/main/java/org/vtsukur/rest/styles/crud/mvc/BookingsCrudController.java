package org.vtsukur.rest.styles.crud.mvc;

import org.javamoney.moneta.Money;
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
import org.vtsukur.rest.core.domain.Room;
import org.vtsukur.rest.core.domain.RoomRepository;
import org.vtsukur.rest.etc.money.Currencies;

import java.time.Period;

/**
 * @author volodymyr.tsukur
 */
@RestController
@RequestMapping("/crud/bookings")
public class BookingsCrudController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> post(@RequestBody BookingSaveRequest request) {
        Room room = roomRepository.findOne(request.getRoomId());
        Booking savedBooking = bookingRepository.save(new Booking(
                request.getCheckIn(),
                request.getCheckOut(),
                Money.of(Period.between(request.getCheckIn(), request.getCheckOut()).getDays(), Currencies.USD).
                        multiply(room.getPrice().getNumber()),
                room,
                Booking.Status.CREATED));
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(MvcUriComponentsBuilder.fromMethodName(BookingsCrudController.class, "getOne", savedBooking.getId()).build().toUri());
        return new ResponseEntity<>(savedBooking, headers, HttpStatus.CREATED);
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

    @RequestMapping(value = "{id}", method = RequestMethod.PATCH, consumes = "application/merge-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> mergeOne(@PathVariable Long id, @RequestBody BookingSaveRequest request) {
        Booking booking = bookingRepository.findOne(id);
        Room room = roomRepository.findOne(request.getRoomId());
        booking.setCheckIn(request.getCheckIn());
        booking.setCheckOut(request.getCheckOut());
        booking.setRoom(room);
        Booking savedBooking = bookingRepository.save(booking);
        return new ResponseEntity<>(savedBooking, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteOne(@PathVariable Long id) {
        bookingRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
