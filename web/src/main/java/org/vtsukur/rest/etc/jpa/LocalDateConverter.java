package org.vtsukur.rest.etc.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;

/**
 * @author volodymyr.tsukur
 */
@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Long> {

    @Override
    public Long convertToDatabaseColumn(final LocalDate attribute) {
        return attribute.toEpochDay();
    }

    @Override
    public LocalDate convertToEntityAttribute(final Long dbData) {
        return LocalDate.ofEpochDay(dbData);
    }

}
