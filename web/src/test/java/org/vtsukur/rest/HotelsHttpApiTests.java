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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.vtsukur.rest.core.domain.Hotel;
import org.vtsukur.rest.core.domain.Room;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.vtsukur.rest.MapBasedHalRepresentations.linkPresent;
import static org.vtsukur.rest.MapBasedHalRepresentations.selfLinkPresent;
import static org.vtsukur.rest.MapBasedRepresentations.propertyMatches;

/**
 * @author volodymyr.tsukur
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class HotelsHttpApiTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
    public void getPage() throws Exception {
        mockMvc.perform(get("/api/hotels")).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaTypes.HAL_JSON)).
                andExpect(jsonPath("$._links.self.href", not(isEmptyOrNullString()))).
                andExpect(jsonPath("$._links.self.templated", is(true))).
                andExpect(jsonPath("$._embedded.hotels", hasSize(2))).
                andExpect(jsonPath("$._embedded.hotels[0]", isHotel(oneOfTheHotels))).
                andExpect(jsonPath("$._embedded.hotels[1]", isHotel(fixture.getLeopolis()))).
                andExpect(jsonPath("$.page.size", is(20))).
                andExpect(jsonPath("$.page.totalElements", is(2))).
                andExpect(jsonPath("$.page.totalPages", is(1))).
                andExpect(jsonPath("$.page.number", is(0)));
    }

    @Test
    public void getOne() throws Exception {
        mockMvc.perform(get("/api/hotels/" + oneOfTheHotels.getId())).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaTypes.HAL_JSON)).
                andExpect(jsonPath("$", isHotel(oneOfTheHotels)));
    }

    @Test
    public void getRooms() throws Exception {
        mockMvc.perform(get("/api/hotels/" + oneOfTheHotels.getId() + "/rooms")).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaTypes.HAL_JSON)).
                andExpect(jsonPath("$._embedded.rooms", hasSize(oneOfTheHotels.getRooms().size()))).
                andExpect(jsonPath("$._embedded.rooms[0]", isRoom(oneOfTheHotels.getRooms().iterator().next())));
    }

    private static MapBasedHotelRepresentationMatcher isHotel(final Hotel hotel) {
        return new MapBasedHotelRepresentationMatcher(hotel);
    }

    private static MapBasedRoomRepresentationMatcher isRoom(final Room room) {
        return new MapBasedRoomRepresentationMatcher(room);
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
            return propertyMatches(item, "name", reference.getName()) &&
                    propertyMatches(item, "propertyType", reference.getPropertyType().toString()) &&
                    propertyMatches(item, "likes", reference.getLikes()) &&
                    propertyMatches(item, "rating.feedbackCount", reference.getRating().getFeedbackCount()) &&
                    propertyMatches(item, "rating.value", reference.getRating().getValue()) &&
                    propertyMatches(item, "address.country", reference.getAddress().getCountry()) &&
                    propertyMatches(item, "address.city", reference.getAddress().getCity()) &&
                    propertyMatches(item, "address.zip", reference.getAddress().getZip()) &&
                    propertyMatches(item, "address.line", reference.getAddress().getLine()) &&
                    linkPresent(item, "rooms") &&
                    selfLinkPresent(item);
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(reference.toString());
        }

    }

    /**
     * @author volodymyr.tsukur
     */
    private static final class MapBasedRoomRepresentationMatcher extends TypeSafeMatcher<Map<String, ?>> {

        private final Room reference;

        private MapBasedRoomRepresentationMatcher(final Room reference) {
            this.reference = reference;
        }

        @Override
        protected boolean matchesSafely(final Map<String, ?> item) {
            return propertyMatches(item, "type", reference.getType()) &&
                    propertyMatches(item, "price", JacksonModulesConfiguration.MoneyModule.format(reference.getPrice())) &&
                    linkPresent(item, "hotel") &&
                    selfLinkPresent(item);
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText(reference.toString());
        }

    }

}
