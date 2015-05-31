package org.vtsukur.rest.core.domain;

import org.springframework.data.rest.core.config.Projection;

/**
 * @author volodymyr.tsukur
 */
@Projection(name = "full", types = Hotel.class)
public interface HotelFullProjection {

    String getName();

    String getCity();

    Rating getRating();

}
