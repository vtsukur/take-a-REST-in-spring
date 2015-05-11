package org.vtsukur.rest.core.domain;

import lombok.*;

/**
 * @author volodymyr.tsukur
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String country;

    private String city;

    private Integer zip;

    private String line;

}
