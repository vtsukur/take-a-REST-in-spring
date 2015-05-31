package org.vtsukur.rest.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author volodymyr.tsukur
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City implements Identifiable<String> {

    @Id
    private String key;

    private Integer propertyCount;

    @Override
    public String getId() {
        return key.toLowerCase();
    }

}
