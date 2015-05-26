package org.vtsukur.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.crud.BookingCreationRequest;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* @author volodymyr.tsukur
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CrudBookingsHttpApiTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper jsonSerializer;

    @Autowired
    private Fixture fixture;

    private Hotel oneOfTheHotels;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        fixture.init();
        oneOfTheHotels = fixture.getNobilis();
    }

    @Test
    public void postBooking() throws Exception {
        final String content = jsonSerializer.writeValueAsString(
                new BookingCreationRequest(
                        LocalDate.of(2015, 9, 1),
                        LocalDate.of(2015, 9, 10),
                        oneOfTheHotels.getId())
        );
        mockMvc.perform(MockMvcRequestBuilders
                .post("/crud/bookings")
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/crud/bookings/1"));
    }

}
