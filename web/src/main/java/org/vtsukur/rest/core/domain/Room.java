package org.vtsukur.rest.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;
import org.vtsukur.rest.etc.jpa.MoneyConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;

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

    @Convert(converter = MoneyConverter.class)
    private Money price;

}
