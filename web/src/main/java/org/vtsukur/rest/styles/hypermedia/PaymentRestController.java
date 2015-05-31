package org.vtsukur.rest.styles.hypermedia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.*;
import org.vtsukur.rest.core.domain.Booking;
import org.vtsukur.rest.core.domain.BookingRepository;

import java.util.UUID;

/**
 * @author volodymyr.tsukur
 */
@RestController
@RequestMapping("/api/bookings/{id}/payment")
public class PaymentRestController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.POST)
    public Receipt pay(@PathVariable("id") Long id, @RequestBody PaymentRequest paymentRequest) {
        Booking booking = bookingRepository.findOne(id);
        booking.setStatus(Booking.Status.PAID);
        bookingRepository.save(booking);
        Receipt receipt = new Receipt(UUID.randomUUID().toString());
        receipt.add(entityLinks.linkToSingleResource(booking));
        return receipt;
    }

    @Data
    public static class PaymentRequest {

        private String cardNumber;

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    public static class Receipt extends ResourceSupport {

        private String transaction;

    }

}
