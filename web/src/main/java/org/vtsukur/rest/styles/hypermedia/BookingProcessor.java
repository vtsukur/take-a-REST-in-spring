package org.vtsukur.rest.styles.hypermedia;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.vtsukur.rest.core.domain.Booking;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author volodymyr.tsukur
 */
@Component
public class BookingProcessor implements ResourceProcessor<Resource<Booking>> {

    @Override
    public Resource<Booking> process(Resource<Booking> resource) {
        Booking booking = resource.getContent();
        if (booking.getStatus() == Booking.Status.CREATED) {
            resource.add(linkTo(methodOn(PaymentRestController.class).pay(booking.getId(), null)).withRel("payment"));
        }
        return resource;
    }

}
