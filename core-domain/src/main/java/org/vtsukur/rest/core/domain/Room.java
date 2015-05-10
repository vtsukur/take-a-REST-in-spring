package org.vtsukur.rest.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;

import javax.persistence.Entity;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Getter
@Setter
public class Room extends BaseEntity {

    private String type;

    private Money price;

}
