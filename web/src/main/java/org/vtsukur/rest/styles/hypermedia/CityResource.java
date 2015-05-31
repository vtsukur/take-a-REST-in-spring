package org.vtsukur.rest.styles.hypermedia;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author volodymyr.tsukur
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CityResource extends ResourceSupport {

    private String name;

}
