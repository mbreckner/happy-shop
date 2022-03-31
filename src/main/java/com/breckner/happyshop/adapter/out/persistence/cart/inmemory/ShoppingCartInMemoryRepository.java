package com.breckner.happyshop.adapter.out.persistence.cart.inmemory;

import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
class ShoppingCartInMemoryRepository {

    private final Map<CartId, ShoppingCart> shoppingCartMap = new HashMap<>();

    public void save(ShoppingCart shoppingCart) {
        shoppingCartMap.put(shoppingCart.getId(), shoppingCart);
    }

    public List<ShoppingCart> findAll() {
        return new ArrayList<>(shoppingCartMap.values());
    }

    public Optional<ShoppingCart> findById(CartId cartId) {
        return Optional.empty();
    }
}
