package com.breckner.happyshop.domain.model;

import com.breckner.happyshop.domain.model.exception.BusinessRuleException;
import com.breckner.happyshop.domain.service.DateTimeHelper;
import lombok.Value;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value(staticConstructor = "of")
public class ShoppingCart {

    CartId id;
    Country country;
    ZonedDateTime createdDate;
    Map<CartItemId, CartItem> items;

    public static ShoppingCart create(Country country) {
        return ShoppingCart.of(
            CartId.generate(),
            country,
            DateTimeHelper.now(),
            new HashMap<>()
        );
    }

    public void addItems(List<CartItem> cartItems) {
        cartItems.forEach(this::addSingleItem);
    }

    private void addSingleItem(CartItem cartItem) {
        if (items.containsKey(cartItem.getId())) {
            throw new BusinessRuleException(String.format("CartItem '%s' already exists", cartItem.getId().getValue()));
        }
        items.put(cartItem.getId(), cartItem);
    }

    public BigDecimal getTotalPrice() {
        return getItems().stream()
            .map(cartItem -> cartItem.getProduct().getUnitPrice().multiply(cartItem.getQuantity()))
            .reduce(BigDecimal.valueOf(0.0), BigDecimal::add);
    }

    public List<CartItem> getItems() {
        return items.values().stream().collect(Collectors.toUnmodifiableList());
    }

}
