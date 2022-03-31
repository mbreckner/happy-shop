package com.breckner.happyshop.application.port.out;

import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.ShoppingCart;

import java.util.Optional;

public interface LoadCartPort {

    Optional<ShoppingCart> byId(CartId id);
}
