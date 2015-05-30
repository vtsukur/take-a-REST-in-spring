package org.vtsukur.rest.money;

import javax.money.CurrencyUnit;
import javax.money.MonetaryCurrencies;

/**
 * @author volodymyr.tsukur
 */
public final class Currencies {

    // Non-instantiable by design.
    private Currencies() {}

    public static final CurrencyUnit USD = MonetaryCurrencies.getCurrency("USD");

}
