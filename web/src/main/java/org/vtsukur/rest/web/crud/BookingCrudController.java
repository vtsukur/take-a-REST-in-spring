package org.vtsukur.rest.web.crud;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author volodymyr.tsukur
 */
@RestController("/crud/bookings")
public class BookingCrudController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String helloWorld() {
        return "Hello World!";
    }

}
