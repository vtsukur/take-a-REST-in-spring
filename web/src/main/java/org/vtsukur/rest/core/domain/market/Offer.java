package org.vtsukur.rest.core.domain.market;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vtsukur.rest.core.domain.BaseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Offer extends BaseEntity {

    private LocalDateTime time;

    private Type type;

    private BigDecimal rate;

    private BigInteger amount;

    private String currency;

    private String description;

    private Status status;

    private enum Type {

        BUY,

        SELL

    }

    private enum Status {

        LISTED,

        CANCELED,

        FINISHED

    }

}
