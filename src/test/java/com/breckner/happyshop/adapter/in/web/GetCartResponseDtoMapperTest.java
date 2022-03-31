package com.breckner.happyshop.adapter.in.web;

import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.breckner.happyshop.domain.service.DateTimeHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class GetCartResponseDtoMapperTest {

    GetCartResponseDtoMapper mapper = new GetCartResponseDtoMapper();

    @Test
    void xx() {
        DateTimeHelper.setClock(Clock.fixed(Instant.parse("2022-03-09T10:15:30Z"), ZoneId.of("UTC")));
        ShoppingCart shoppingCart = ShoppingCart.of(
            CartId.of("id_1"),
            Country.SWITZERLAND,
            DateTimeHelper.now(),
            Map.of()
        );

        GetCartController.GetCartResponseDto responseDto = mapper.toResponseDto(shoppingCart);

        assertThat(responseDto.getId(), is("id_1"));
        assertThat(responseDto.getCountryCode(), is("CHE"));
        assertThat(responseDto.getCountryName(), is("Switzerland"));
        assertThat(responseDto.getCurrency(), is("CHF"));
        assertThat(responseDto.getTotalPrice(), is(BigDecimal.valueOf(0.00)));
        assertThat(responseDto.getCreated(), is(DateTimeHelper.now()));

    }

}