package com.breckner.happyshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
@EqualsAndHashCode
public class CartItem {

    CartItemId id;
    Product product;
    BigDecimal quantity;

    public static CartItem of(String plainId, String plainBarcode, BigDecimal unitPrice, String description, BigDecimal quantity) {
        return CartItem.of(
            CartItemId.of(plainId),
            Product.of(Barcode.of(plainBarcode), unitPrice, description),
            quantity
        );
    }
}
