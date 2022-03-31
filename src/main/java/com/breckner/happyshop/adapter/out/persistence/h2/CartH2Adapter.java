package com.breckner.happyshop.adapter.out.persistence.h2;

import com.breckner.happyshop.application.port.out.PersistCartPort;
import com.breckner.happyshop.domain.model.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
class CartH2Adapter implements PersistCartPort {

    @Override
    public void persist(ShoppingCart shoppingCart) {

    }
}
