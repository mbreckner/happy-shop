package com.breckner.happyshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigInteger;

@Value(staticConstructor = "of")
@EqualsAndHashCode
public class CartItem {

    CartItemId id;
    Product product;
    BigInteger quantity;
}
