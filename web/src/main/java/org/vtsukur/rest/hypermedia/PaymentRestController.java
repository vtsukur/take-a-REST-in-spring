package org.vtsukur.rest.hypermedia;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import org.vtsukur.rest.core.domain.Booking;
import org.vtsukur.rest.core.domain.BookingRepository;

/**
 * @author volodymyr.tsukur
 */
@RestController
@RequestMapping("/api/bookings/{id}/payment")
public class PaymentRestController {

    @Autowired
    private BookingRepository bookingRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Resource<Booking> pay(
            @PathVariable("id") Booking booking,
            @RequestBody PaymentRequest paymentRequest) {
        booking.setStatus(Booking.Status.PAID);
        return new Resource<>(bookingRepository.save(booking));
    }

    @Getter
    @Setter
    public static class PaymentRequest {

        private String cardNumber;

    }

}
