package org.vtsukur.rest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.javamoney.moneta.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.io.IOException;
import java.util.Locale;

/**
 * @author volodymyr.tsukur
 */
@Configuration
public class JacksonMoneyModule {

    @Bean
    public Module newMoneyModule() {
        return new MoneyModule();
    }

    /**
     * @author volodymyr.tsukur
     */
    public static final class MoneyModule extends SimpleModule {

        private static final MonetaryAmountFormat FORMAT = MonetaryFormats.getAmountFormat(Locale.ROOT);

        public MoneyModule() {
            addSerializer(Money.class, new MoneySerializer());
            addValueInstantiator(Money.class, new MoneyValueInstantiator());
        }

        public static String format(final Money value) {
            return FORMAT.format(value);
        }

        public static Money parse(final String serialized) {
            return Money.parse(serialized, FORMAT);
        }

        /**
         * @author volodymyr.tsukur
         */
        static final class MoneySerializer extends ToStringSerializer {

            @Override
            public void serialize(final Object value, final JsonGenerator generator, final SerializerProvider ignored) throws IOException {
                generator.writeString(format((Money) value));
            }

        }

        /**
         * @author volodymyr.tsukur
         */
        static final class MoneyValueInstantiator extends ValueInstantiator {

            @Override
            public String getValueTypeDesc() {
                return Money.class.toString();
            }

            @Override
            public boolean canCreateFromString() {
                return true;
            }

            @Override
            public Object createFromString(final DeserializationContext ignored, final String value) throws IOException {
                return parse(value);
            }

        }

    }

}
