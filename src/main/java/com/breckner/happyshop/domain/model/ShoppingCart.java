package com.breckner.happyshop.domain.model;

import com.breckner.happyshop.domain.service.DateTimeHelper;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Value(staticConstructor = "of")
public class ShoppingCart {

    UUID id;
    Country country;
    ZonedDateTime createdDate;
    List<Product> products;

    public static ShoppingCart create(Country country) {
        return ShoppingCart.of(
            UUID.randomUUID(),
            country,
            DateTimeHelper.now(),
            List.of()
        );
    }


}
