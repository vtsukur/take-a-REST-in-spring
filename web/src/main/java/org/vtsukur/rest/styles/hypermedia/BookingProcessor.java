package org.vtsukur.rest.styles.hypermedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.vtsukur.rest.core.domain.Booking;

/**
 * @author volodymyr.tsukur
 */
@Component
public class BookingProcessor implements ResourceProcessor<Resource<Booking>> {

    @Autowired
    private EntityLinks entityLinks;

    @Override
    public Resource<Booking> process(Resource<Booking> resource) {
        if (resource.getContent().getStatus() == Booking.Status.CREATED) {
            resource.add(entityLinks.linkForSingleResource(resource.getContent()).withRel("payment"));
        }
        return resource;
    }

}
