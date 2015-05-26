package org.vtsukur.rest.crud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author volodymyr.tsukur
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingCreationRequest {

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long hotelId;

}
