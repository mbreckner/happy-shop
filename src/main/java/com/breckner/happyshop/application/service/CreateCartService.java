package com.breckner.happyshop.application.service;

import com.breckner.happyshop.application.port.in.CreateCartUseCase;
import com.breckner.happyshop.application.port.out.PersistCartPort;
import com.breckner.happyshop.domain.model.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CreateCartService implements CreateCartUseCase {

    private final PersistCartPort persistCartPort;

    @Override
    public ShoppingCart create(CreateCartInput input) {

        ShoppingCart shoppingCart = ShoppingCart.create(input.getCountry());

        persistCartPort.persist(shoppingCart);

        return shoppingCart;
    }
}
