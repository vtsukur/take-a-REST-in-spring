package org.vtsukur.rest.crud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author volodymyr.tsukur
 */
@Getter
@Setter
@AllArgsConstructor
public class BookingCreationRequest {

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long hotelId;

}
