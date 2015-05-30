package org.vtsukur.rest.core.domain;

import lombok.*;

import javax.persistence.Embeddable;

/**
 * @author volodymyr.tsukur
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String country;

    private String city;

    private Integer zip;

    private String line;

}
