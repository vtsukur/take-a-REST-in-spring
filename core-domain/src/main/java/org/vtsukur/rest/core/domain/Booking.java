package org.vtsukur.rest.core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Getter
@Setter
@ToString
public class Booking extends BaseEntity {

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Money price;

}
