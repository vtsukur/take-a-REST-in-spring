package org.vtsukur.rest.core.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author volodymyr.tsukur
 */
@MappedSuperclass
@Getter
@EqualsAndHashCode
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

}
