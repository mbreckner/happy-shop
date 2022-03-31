package com.breckner.happyshop.adapter.out.persistence.cart.inmemory;

import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.breckner.happyshop.domain.service.DateTimeHelper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ShoppingCartInMemoryAdapterTest {

    private ShoppingCartInMemoryRepository shoppingCartInMemoryRepository = new ShoppingCartInMemoryRepository();

    private ShoppingCartInMemoryAdapter adapterUnderTest = new ShoppingCartInMemoryAdapter(shoppingCartInMemoryRepository);

    @Test
    void shouldPersistNewShoppingCart() {
        ShoppingCart shoppingCart = ShoppingCart.of(
            CartId.of("1"), Country.SWITZERLAND,
            DateTimeHelper.now(), Map.of()
        );

        adapterUnderTest.persist(shoppingCart);

        assertThat(shoppingCartInMemoryRepository.findAll().size(), is(1));
    }

    @Test
    void shouldUpdateExistingShoppingCart() {
        ShoppingCart existingShoppingCart = mockShoppingCart("1");
        shoppingCartInMemoryRepository.save(existingShoppingCart);
        ShoppingCart shoppingCartToUpdate = mockShoppingCart("1");

        adapterUnderTest.persist(shoppingCartToUpdate);

        assertThat(shoppingCartInMemoryRepository.findAll().size(), is(1));

    }

    private ShoppingCart mockShoppingCart(String id) {
        return ShoppingCart.of(
            CartId.of(id), Country.SWITZERLAND,
            DateTimeHelper.now(), Map.of()
        );
    }

}