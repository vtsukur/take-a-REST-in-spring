package org.vtsukur.rest.styles.crud.mvc;

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
public class BookingSaveRequest {

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Long roomId;

}
