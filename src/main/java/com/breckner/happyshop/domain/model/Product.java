package com.breckner.happyshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
@EqualsAndHashCode
public class Product {

    Barcode barcode;
    BigDecimal unitPrice;
    String description;
}
