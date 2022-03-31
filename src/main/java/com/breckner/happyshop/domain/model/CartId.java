package com.breckner.happyshop.domain.model;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class CartId {

    String value;

    public static CartId generate() {
        return CartId.of(UUID.randomUUID().toString());
    }
}
