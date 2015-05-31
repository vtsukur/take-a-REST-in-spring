package org.vtsukur.rest;

import org.javamoney.moneta.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
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
import org.vtsukur.rest.etc.money.Currencies;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author volodymyr.tsukur
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class PaymentHttpApiTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
                Money.of(500, Currencies.USD),
                oneOfTheHotels.getRooms().iterator().next(),
                Booking.Status.CREATED
        );
    }

    @Test
    public void payment() throws Exception {
        bookingRepository.save(referenceBooking);

        final String content = paymentJsonString();
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/bookings/" + referenceBooking.getId() + "/payment")
                .accept(MediaTypes.HAL_JSON)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
        final Booking paidBooking = bookingRepository.findOne(referenceBooking.getId());
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction", is(notNullValue())))
                .andExpect(jsonPath("$._links.booking", is(notNullValue())));
        Assert.assertThat(paidBooking.getStatus(), is(Booking.Status.PAID));
    }

    private static String paymentJsonString() {
        return "{\"cardNumber\":\"1234 5678 9012 3456\"}";
    }

}
