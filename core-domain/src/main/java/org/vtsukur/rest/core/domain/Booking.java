package org.vtsukur.rest.core.domain;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity {

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Money price;

}
