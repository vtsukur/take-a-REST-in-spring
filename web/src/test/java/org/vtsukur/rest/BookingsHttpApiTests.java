package org.vtsukur.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.rest.webmvc.RestMediaTypes;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.vtsukur.rest.core.domain.Booking;
import org.vtsukur.rest.core.domain.BookingRepository;
import org.vtsukur.rest.core.domain.Hotel;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.vtsukur.rest.MapBasedBookingHalRepresentationMatcher.isBooking;

/**
 * @author volodymyr.tsukur
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class BookingsHttpApiTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper jsonSerializer;

    @Autowired
    private Fixture fixture;

    @Autowired
    private BookingRepository bookingRepository;

    private Hotel oneOfTheHotels;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        fixture.init();
        oneOfTheHotels = fixture.getNobilis();
    }

    @Test
    public void postBooking() throws Exception {
        final String content = "{" +
                "\"checkIn\": [ 2015, 9, 1 ]," +
                "\"checkOut\": [ 2015, 9, 10 ]," +
                "\"hotel\": \"/api/hotels/" + oneOfTheHotels.getId() + "\"" +
                "}";
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/bookings")
                .accept(MediaTypes.HAL_JSON)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
        final Booking createdBooking = bookingRepository.findFirstByOrderByIdDesc();
        resultActions.andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/api/bookings/" + createdBooking.getId()))
                .andExpect(jsonPath("$", isBooking(createdBooking)));
    }

    @Test
    public void patchBooking() throws Exception {
        final Booking booking = new Booking(
                LocalDate.of(2015, 9, 1),
                LocalDate.of(2015, 9, 10),
                oneOfTheHotels
        );
        bookingRepository.save(booking);

        final String content = "{" +
                "\"checkIn\": [ 2015, 9, 11 ]," +
                "\"checkOut\": [ 2015, 9, 20 ]," +
                "\"hotel\": \"/api/hotels/" + oneOfTheHotels.getId() + "\"" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/bookings/" + booking.getId())
                .accept(MediaTypes.HAL_JSON)
                .content(content)
                .contentType(RestMediaTypes.MERGE_PATCH_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isBooking(booking)));
    }

}
