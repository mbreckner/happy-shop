package com.breckner.happyshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
@EqualsAndHashCode
public class CartItemId {

    String value;
}
