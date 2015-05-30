package org.vtsukur.rest.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseEntity {

    private String type;

    @Lob
    private Money price;

}
