package org.vtsukur.rest.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;

import java.time.LocalDate;

/**
 * @author volodymyr.tsukur
 */
@Getter
@Setter
public class Booking extends BaseEntity {

    private Room room;

    private LocalDate from;

    private LocalDate to;

    private Money price;

}
