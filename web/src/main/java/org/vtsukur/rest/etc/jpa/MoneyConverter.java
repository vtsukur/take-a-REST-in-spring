package org.vtsukur.rest.etc.jpa;

import org.javamoney.moneta.Money;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;

/**
 * @author volodymyr.tsukur
 */
@Converter
public class MoneyConverter implements AttributeConverter<Money, String> {

    private static final MonetaryAmountFormat FORMAT = MonetaryFormats.getAmountFormat(Locale.ROOT);

    @Override
    public String convertToDatabaseColumn(final Money attribute) {
        return FORMAT.format(attribute);
    }

    @Override
    public Money convertToEntityAttribute(String dbData) {
        return Money.parse(dbData, FORMAT);
    }

}
