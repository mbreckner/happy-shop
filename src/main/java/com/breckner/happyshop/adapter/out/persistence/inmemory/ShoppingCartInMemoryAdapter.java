package com.breckner.happyshop.adapter.out.persistence.inmemory;

import com.breckner.happyshop.application.port.out.LoadCartPort;
import com.breckner.happyshop.application.port.out.PersistCartPort;
import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.ShoppingCart;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ShoppingCartInMemoryAdapter implements PersistCartPort, LoadCartPort {

    private final ShoppingCartInMemoryRepository shoppingCartInMemoryRepository;

    @Override
    public void persist(ShoppingCart shoppingCart) {
        shoppingCartInMemoryRepository.save(shoppingCart);
    }

    @Override
    public Optional<ShoppingCart> byId(CartId id) {
        return Optional.empty();
    }
}
