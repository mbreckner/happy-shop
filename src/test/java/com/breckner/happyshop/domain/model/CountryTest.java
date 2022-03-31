package com.breckner.happyshop.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CountryTest {

    @Test
    void shouldReturnSwitzerlandWhenCodeIsValid() {
        assertThat(Country.byCode("CHE"), is(Country.SWITZERLAND));
    }

    @Test
    void shouldReturnNullWhenCodeIsInvalid() {
        Assertions.assertNull(Country.byCode("invalid"));
    }

    @Test
    void shouldReturnNullWhenCodeIsNull() {
        Assertions.assertNull(null);
    }

}