package org.vtsukur.rest.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Getter
@Setter
@ToString(exclude = "hotel")
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    private Hotel hotel;

    private String type;

    @Lob
    private Money price;

}
