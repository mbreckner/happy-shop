package com.breckner.happyshop.application.port.out;

import com.breckner.happyshop.domain.model.ShoppingCart;

public interface PersistShoppingCartPort {

    void persist(ShoppingCart shoppingCart);
}
