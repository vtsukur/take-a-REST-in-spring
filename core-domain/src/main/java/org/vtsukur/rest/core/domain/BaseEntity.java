package org.vtsukur.rest.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author volodymyr.tsukur
 */
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

}
