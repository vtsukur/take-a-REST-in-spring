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

    private Booking referenceBooking;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        fixture.init();
        Hotel oneOfTheHotels = fixture.getNobilis();

        referenceBooking = new Booking(
                LocalDate.of(2015, 9, 1),
                LocalDate.of(2015, 9, 10),
                null,
                oneOfTheHotels,
                Booking.Status.CREATED
        );
    }

    @Test
    public void post() throws Exception {
        final String content = saveRequestJsonString(referenceBooking);
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
    public void mergeOne() throws Exception {
        bookingRepository.save(referenceBooking);

        final String content = saveRequestJsonString(
                new Booking(
                        referenceBooking.getCheckIn().plusDays(10),
                        referenceBooking.getCheckOut().plusDays(10),
                        null,
                        referenceBooking.getHotel(),
                        Booking.Status.CREATED)
        );
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/api/bookings/" + referenceBooking.getId())
                .accept(MediaTypes.HAL_JSON)
                .content(content)
                .contentType(RestMediaTypes.MERGE_PATCH_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isBooking(referenceBooking)));
    }

    @Test
    public void deleteOne() throws Exception {
        bookingRepository.save(referenceBooking);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/bookings/" + referenceBooking.getId()))
                .andExpect(status().isNoContent());
    }

    private static String saveRequestJsonString(final Booking booking) {
        return "{" +
                "\"checkIn\": " + localDateToJsonArrayString(booking.getCheckIn()) + "," +
                "\"checkOut\": " + localDateToJsonArrayString(booking.getCheckOut()) + "," +
                "\"status\": \"CREATED\"," +
                "\"hotel\": \"/api/hotels/" + booking.getHotel().getId() + "\"" +
                "}";
    }

    private static String localDateToJsonArrayString(final LocalDate date) {
        return "[ " + date.getYear() + ", " + date.getMonthValue() + ", " + date.getDayOfMonth() + "]";
    }

}
