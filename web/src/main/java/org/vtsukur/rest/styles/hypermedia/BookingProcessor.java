package org.vtsukur.rest.styles.hypermedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
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

    @Autowired
    private EntityLinks entityLinks;

    @Override
    public Resource<Booking> process(Resource<Booking> resource) {
        Booking booking = resource.getContent();
        if (booking.getStatus() == Booking.Status.CREATED) {
            resource.add(linkTo(methodOn(PaymentRestController.class).pay(booking.getId(), null)).withRel("payment"));
            resource.add(entityLinks.linkToSingleResource(booking).withRel("update"));
            resource.add(entityLinks.linkToSingleResource(booking).withRel("cancel"));
        }
        return resource;
    }

}
