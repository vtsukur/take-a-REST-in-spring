package org.vtsukur.rest;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.core.domain.HotelRepository;

import java.util.Arrays;
import java.util.Map;

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
                andExpect(jsonPath("$._embedded.hotels[0]", isHotel(nobilis))).
                andExpect(jsonPath("$._embedded.hotels[1]", isHotel(leopolis))).
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
                andExpect(jsonPath("$", isHotel(nobilis)));
    }

    private static MapBasedHotelRepresentationMatcher isHotel(final Hotel hotel) {
        return new MapBasedHotelRepresentationMatcher(hotel);
    }

    /**
     * @author volodymyr.tsukur
     */
    private static final class MapBasedHotelRepresentationMatcher extends TypeSafeMatcher<Map<String, ?>> {

        private final Hotel reference;

        private MapBasedHotelRepresentationMatcher(final Hotel reference) {
            this.reference = reference;
        }

        @Override
        protected boolean matchesSafely(final Map<String, ?> item) {
            return nameMatches(item) && selfLinkPresent(item);
        }

        private boolean nameMatches(final Map<String, ?> item) {
            return item.get("name").equals(reference.getName());
        }

        private boolean selfLinkPresent(final Map<String, ?> item) {
            final Map<?, ?> _links = subMap(item, "_links");
            if (_links == null) {
                return false;
            }

            final Map<?, ?> self = subMap(_links, "self");
            if (self == null) {
                return false;
            }

            final String href = (String) self.get("href");

            return !href.trim().isEmpty();
        }

        private static Map<?, ?> subMap(final Map<?, ?> source, final String key) {
            return source.get(key) instanceof Map ? (Map<?, ?>) source.get(key) : null;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(reference.toString());
        }

    }

}
