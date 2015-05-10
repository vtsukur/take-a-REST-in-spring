package org.vtsukur.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.JsonPathExpectationsHelper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.core.domain.HotelRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author volodymyr.tsukur
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public final class HotelsHttpApiTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private HotelRepository hotelRepository;

    private Hotel nobilis;

    private Hotel leopolis;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        hotelRepository.deleteAll();

        nobilis = new Hotel("Nobilis");
        leopolis = new Hotel("Leopolis");

        hotelRepository.save(Arrays.asList(nobilis, leopolis));
    }

    @Test
    public void getHotels() throws Exception {
        mockMvc.perform(get("/api/hotels")).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaTypes.HAL_JSON)).
                andExpect(jsonPath("$._links.self.href", not(isEmptyOrNullString()))).
                andExpect(jsonPath("$._links.self.templated", is(true))).
                andExpect(jsonPath("$._embedded.hotels", hasSize(2))).
                andExpect(hasHotel("$._embedded.hotels[0]", nobilis)).
                andExpect(hasHotel("$._embedded.hotels[1]", leopolis)).
                andExpect(jsonPath("$.page.size", is(20))).
                andExpect(jsonPath("$.page.totalElements", is(2))).
                andExpect(jsonPath("$.page.totalPages", is(1))).
                andExpect(jsonPath("$.page.number", is(0)));
    }

    @Test
    public void getHotel() throws Exception {
        mockMvc.perform(get("/api/hotels/" + nobilis.getId())).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaTypes.HAL_JSON)).
                andExpect(hasHotel("$", nobilis));
    }

    private static ResultMatcher hasHotel(final String prefix, final Hotel hotel) {
        return (mvcResult) -> {
            final String content = mvcResult.getResponse().getContentAsString();
            new JsonPathExpectationsHelper(prefix + ".name").
                    assertValue(content, is(hotel.getName()));
            new JsonPathExpectationsHelper(prefix + "._links.self.href").
                    assertValue(content, not(isEmptyOrNullString()));
        };
    }

}
