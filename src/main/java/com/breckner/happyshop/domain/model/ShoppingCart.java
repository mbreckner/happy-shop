package com.breckner.happyshop.domain.model;

import com.breckner.happyshop.domain.service.DateTimeHelper;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Value(staticConstructor = "of")
public class ShoppingCart {

    Country country;
    ZonedDateTime createdDate;
    List<Product> products;

    public static ShoppingCart create(Country country) {
        return ShoppingCart.of(
            country,
            DateTimeHelper.now(),
            List.of()
        );
    }


}
