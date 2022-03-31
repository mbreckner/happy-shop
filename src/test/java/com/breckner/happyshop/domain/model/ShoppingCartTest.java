package com.breckner.happyshop.domain.model;

import com.breckner.happyshop.domain.model.exception.BusinessRuleException;
import com.breckner.happyshop.domain.service.DateTimeHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ShoppingCartTest {

    @Test
    void shouldCreateShoppingCart() {
        String instantExpected = "2022-03-09T10:15:30Z";
        DateTimeHelper.setClock(Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC")));

        ShoppingCart shoppingCart = ShoppingCart.create(Country.SWITZERLAND);

        assertThat(shoppingCart.getId(), is(notNullValue()));
        assertThat(shoppingCart.getCountry(), is(Country.SWITZERLAND));
        assertThat(shoppingCart.getCreatedDate(), is(DateTimeHelper.now()));
        assertThat("products should be empty", shoppingCart.getItems().isEmpty());
    }

    @Test
    void shouldAddCartItems() {
        Map<CartItemId, CartItem> cartItemMap = new HashMap<>();
        CartItem existingCartItem = mockCartItem("existing");
        cartItemMap.put(existingCartItem.getId(), existingCartItem);
        ShoppingCart shoppingCart = ShoppingCart.of(
            CartId.of("1"), Country.SWITZERLAND, DateTimeHelper.now(),
            cartItemMap);
        List<CartItem> cartItems = List.of(
            mockCartItem("new")
        );

        shoppingCart.addItems(cartItems);

        assertThat(shoppingCart.getItems().size(), is(2));
    }

    @Test
    void shouldThrowBusinessException_WhenItemAlreadyExists() {
        CartItem existingCartItem = mockCartItem("existing");
        ShoppingCart shoppingCart = ShoppingCart.of(
            CartId.of("1"), Country.SWITZERLAND, DateTimeHelper.now(),
            Map.of(existingCartItem.getId(), existingCartItem));
        List<CartItem> cartItems = List.of(
            existingCartItem,
            mockCartItem("new")
        );

        Assertions.assertThrows(BusinessRuleException.class, () -> shoppingCart.addItems(cartItems));
    }

    @Test
    void shouldGetTotalPrice() {
        ShoppingCart shoppingCart = ShoppingCart.of(
            CartId.of("1"), Country.GERMANY, DateTimeHelper.now(),
            Map.of(
                CartItemId.of("1"),
                CartItem.of(CartItemId.of("1"), mockProduct(BigDecimal.valueOf(1.20)), BigDecimal.valueOf(5)),
                CartItemId.of("2"),
                CartItem.of(CartItemId.of("2"), mockProduct(BigDecimal.valueOf(33.10)), BigDecimal.valueOf(2))
            )
        );

        assertThat(shoppingCart.getTotalPrice(), is(BigDecimal.valueOf(72.20)));
    }

    @Test
    void shouldGetCopyOfCartItems() {
        ShoppingCart shoppingCart = ShoppingCart.of(CartId.of("1"), Country.SWITZERLAND, DateTimeHelper.now(),
            Map.of(CartItemId.of("1"), mockCartItem("1")));

        List<CartItem> returnedCartItems = shoppingCart.getItems();

        Assertions.assertThrows(
            UnsupportedOperationException.class,
            () -> returnedCartItems.add(mockCartItem("2"))
        );
    }

    private void assertDateIsNow(ZonedDateTime date) {
        ZonedDateTime now = ZonedDateTime.now();
        assertThat("should be equal or after now", date.isAfter(now) || date.equals(now));
        assertThat("should be before now + 1min", date.isBefore(now.plusMinutes(1)));
    }

    private CartItem mockCartItem(String id) {
        return CartItem.of(CartItemId.of(id), null, null);
    }

    private Product mockProduct(BigDecimal unitPrice) {
        return Product.of(Barcode.of("1"), unitPrice, "name");
    }

}