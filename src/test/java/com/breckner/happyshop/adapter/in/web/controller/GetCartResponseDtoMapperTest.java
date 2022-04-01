package com.breckner.happyshop.adapter.in.web.controller;

import com.breckner.happyshop.adapter.in.web.controller.GetCartResponseDtoMapper;
import com.breckner.happyshop.domain.model.*;
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
    void shouldMap() {
        DateTimeHelper.setClock(Clock.fixed(Instant.parse("2022-03-09T10:15:30Z"), ZoneId.of("UTC")));
        ShoppingCart shoppingCart = ShoppingCart.of(
            CartId.of("id_1"),
            Country.SWITZERLAND,
            DateTimeHelper.now(),
            Map.of(CartItemId.of("1"), CartItem.of(
                "1", "barcode",
                BigDecimal.valueOf(0.60), "bananas",
                BigDecimal.valueOf(5)
            ))
        );

        GetCartController.GetCartResponseDto responseDto = mapper.toResponseDto(shoppingCart);

        assertThat(responseDto.getId(), is("id_1"));
        assertThat(responseDto.getCountryCode(), is("CHE"));
        assertThat(responseDto.getCountryName(), is("Switzerland"));
        assertThat(responseDto.getCurrency(), is("CHF"));
        assertThat(responseDto.getTotalPrice(), is(BigDecimal.valueOf(3.00)));
        assertThat(responseDto.getCreated(), is(DateTimeHelper.now()));
        assertThat(responseDto.getCartItems().size(), is(1));
        assertThat(responseDto.getCartItems().get(0).getId(), is("1"));
        assertThat(responseDto.getCartItems().get(0).getQuantity(), is(BigDecimal.valueOf(5)));
        assertThat(responseDto.getCartItems().get(0).getProduct().getBarcode(), is("barcode"));
        assertThat(responseDto.getCartItems().get(0).getProduct().getUnitPrice(), is(BigDecimal.valueOf(0.60)));
        assertThat(responseDto.getCartItems().get(0).getProduct().getDescription(), is("bananas"));

    }

}