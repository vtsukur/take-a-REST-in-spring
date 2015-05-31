package org.vtsukur.rest.core.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author volodymyr.tsukur
 */
@Projection(name = "custom", types = Hotel.class)
public interface HotelCustomProjection {

    String getName();

    @Value("#{target.rating.value} - #{target.rating.feedbackCount}")
    String getRatingInfo();

}
