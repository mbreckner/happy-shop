package com.breckner.happyshop.adapter.out.persistence.h2;

import com.breckner.happyshop.application.port.out.PersistShoppingCartPort;
import com.breckner.happyshop.domain.model.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
class ShoppingCartH2Adapter implements PersistShoppingCartPort {

    @Override
    public void persist(ShoppingCart shoppingCart) {

    }
}
