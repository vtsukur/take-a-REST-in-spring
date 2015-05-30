package org.vtsukur.rest.etc.jpa;

import org.javamoney.moneta.Money;
import org.vtsukur.rest.JacksonModulesConfiguration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author volodymyr.tsukur
 */
@Converter
public class MoneyConverter implements AttributeConverter<Money, String> {

    @Override
    public String convertToDatabaseColumn(final Money attribute) {
        return JacksonModulesConfiguration.MoneyModule.format(attribute);
    }

    @Override
    public Money convertToEntityAttribute(String dbData) {
        return JacksonModulesConfiguration.MoneyModule.parse(dbData);
    }

}
