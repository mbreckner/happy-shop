package com.breckner.happyshop.application.port.out;

import com.breckner.happyshop.domain.model.ShoppingCart;

public interface PersistCartPort {

    void persist(ShoppingCart shoppingCart);
}
