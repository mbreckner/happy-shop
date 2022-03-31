package com.breckner.happyshop.domain.model;

import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Country {

    SWITZERLAND("CHE", "Switzerland", Currency.CHF),
    GERMANY("DEU", "Germany", Currency.EUR),
    AUSTRIA("AUT", "Austria", Currency.EUR);

    // ISO-3166
    public final String code;
    public final String displayName;
    public final Currency currency;

    public static Country byCode(String code) {
        return Stream.of(Country.values())
            .filter(country -> country.code.equals(code))
            .findFirst()
            .orElse(null);

    }
}
