package com.breckner.happyshop.domain;

import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.breckner.happyshop.domain.service.DateTimeHelper;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ShoppingCartTest {

    @Test
    void shouldCreateShoppingCart() {
        String instantExpected = "2022-03-09T10:15:30Z";
        DateTimeHelper.setClock(Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC")));

        ShoppingCart shoppingCart = ShoppingCart.create(Country.SWITZERLAND);

        assertThat(shoppingCart.getCountry(), is(Country.SWITZERLAND));
        assertThat(shoppingCart.getCreatedDate(), is(DateTimeHelper.now()));
        assertThat("products should be empty", shoppingCart.getProducts().isEmpty());
    }

    private void assertDateIsNow(ZonedDateTime date) {
        ZonedDateTime now = ZonedDateTime.now();
        assertThat("should be equal or after now", date.isAfter(now) || date.equals(now));
        assertThat("should be before now + 1min", date.isBefore(now.plusMinutes(1)));
    }

}