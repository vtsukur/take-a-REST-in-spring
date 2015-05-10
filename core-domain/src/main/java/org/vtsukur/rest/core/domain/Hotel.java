package org.vtsukur.rest.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Getter
@Setter
public class Hotel extends BaseEntity {

    private String name;

}
