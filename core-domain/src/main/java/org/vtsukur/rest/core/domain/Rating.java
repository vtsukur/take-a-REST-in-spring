package org.vtsukur.rest.core.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * @author volodymyr.tsukur
 */
@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    private BigDecimal value;

    private Integer feedbackCount;

}
