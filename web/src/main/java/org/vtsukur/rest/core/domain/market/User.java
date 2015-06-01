package org.vtsukur.rest.core.domain.market;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vtsukur.rest.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    private String login;

    private String phoneNumber;

    @OneToMany
    private Set<Offer> offers = new HashSet<>();

}
