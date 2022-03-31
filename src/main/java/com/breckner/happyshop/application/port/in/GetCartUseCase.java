package com.breckner.happyshop.application.port.in;

import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.ShoppingCart;

public interface GetCartUseCase {

    ShoppingCart get(CartId id);
}
