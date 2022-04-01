package com.breckner.happyshop.adapter.out.persistence.cart.jpa;

import com.breckner.happyshop.domain.model.*;
import com.breckner.happyshop.domain.service.DateTimeHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@ContextConfiguration(classes = {
    ShoppingCartJpaAdapter.class,
    ShoppingCartJpaRepository.class,
    ShoppingCartEntityMapper.class
})
@EnableJpaRepositories
@EntityScan
class ShoppingCartJpaAdapterTest {

    private ShoppingCartJpaAdapter adapterUnderTest;

    @Autowired
    private ShoppingCartJpaRepository shoppingCartJpaRepository;
    @Autowired
    private ShoppingCartEntityMapper shoppingCartEntityMapper;

    private final ZonedDateTime createdDate = Instant.parse("2022-03-09T10:15:30Z").atZone(ZoneId.systemDefault());

    @BeforeEach
    void setUp() {
        adapterUnderTest = new ShoppingCartJpaAdapter(shoppingCartJpaRepository, shoppingCartEntityMapper);
    }

    @Test
    void shouldSaveNewShoppingCart() {
        ShoppingCart shoppingCart = mockShoppingCart("1", Country.SWITZERLAND);

        adapterUnderTest.persist(shoppingCart);

        List<ShoppingCartJpaEntity> shoppingCartEntities = shoppingCartJpaRepository.findAll();
        assertThat(shoppingCartEntities.size(), is(1));
        assertThat(shoppingCartEntities.get(0).getId(), is("1"));
        assertThat(shoppingCartEntities.get(0).getCountryCode(), is("CHE"));
        assertThat(shoppingCartEntities.get(0).getCreatedDate(), is(createdDate));
        assertThat(shoppingCartEntities.get(0).getItems().get(0).getCartItemId(), is("1"));
        assertThat(shoppingCartEntities.get(0).getItems().get(0).getBarcode(), is("bc"));
        assertThat(shoppingCartEntities.get(0).getItems().get(0).getQuantity(), is(BigDecimal.ONE));
        assertThat(shoppingCartEntities.get(0).getItems().get(0).getUnitPrice(), is(BigDecimal.TEN));
        assertThat(shoppingCartEntities.get(0).getItems().get(0).getDescription(), is("desc"));

    }

    @Test
    void shouldUpdateExistingShoppingCart() {
        ShoppingCartJpaEntity existingShoppingCartEntity = new ShoppingCartJpaEntity(
            "1", "CHE", DateTimeHelper.now(), List.of()
        );
        shoppingCartJpaRepository.save(existingShoppingCartEntity);
        ShoppingCart shoppingCartToUpdate = mockShoppingCart("1", Country.GERMANY);

        adapterUnderTest.persist(shoppingCartToUpdate);

        List<ShoppingCartJpaEntity> shoppingCartJpaEntities = shoppingCartJpaRepository.findAll();
        assertThat(shoppingCartJpaEntities.size(), is(1));
        assertThat(shoppingCartJpaEntities.get(0).getId(), is("1"));
        assertThat(shoppingCartJpaEntities.get(0).getCountryCode(), is("DEU"));
        assertThat(shoppingCartJpaEntities.get(0).getCreatedDate(), is(createdDate));
        assertThat(shoppingCartJpaEntities.get(0).getItems().size(), is(1));
        assertThat(shoppingCartJpaEntities.get(0).getItems().get(0).getCartItemId(), is("1"));
        assertThat(shoppingCartJpaEntities.get(0).getItems().get(0).getBarcode(), is("bc"));
        assertThat(shoppingCartJpaEntities.get(0).getItems().get(0).getQuantity(), is(BigDecimal.ONE));
        assertThat(shoppingCartJpaEntities.get(0).getItems().get(0).getUnitPrice(), is(BigDecimal.TEN));
        assertThat(shoppingCartJpaEntities.get(0).getItems().get(0).getDescription(), is("desc"));

    }

    @Test
    @Sql(scripts={"classpath:adapter/persistence/shopping_carts.sql"})
    void shouldLoadExistingShoppingCart() {
        Optional<ShoppingCart> shoppingCart = adapterUnderTest.byId(CartId.of("cart1"));

        assertThat("shoppingCart may not be empty", shoppingCart.isPresent());
        assertThat(shoppingCart.get().getId(), is(CartId.of("cart1")));
        assertThat(shoppingCart.get().getCountry(), is(Country.SWITZERLAND));
        assertThat(shoppingCart.get().getCreatedDate(), is(createdDate));

        assertThat(shoppingCart.get().getItems().get(0).getId().getValue(), is("item1"));
        assertThat(shoppingCart.get().getItems().get(0).getProduct().getBarcode().getValue(), is("barcode1"));
        assertThat(shoppingCart.get().getItems().get(0).getProduct().getUnitPrice(), is(new BigDecimal("1.00")));
        assertThat(shoppingCart.get().getItems().get(0).getQuantity(), is(new BigDecimal("3.00")));
        assertThat(shoppingCart.get().getItems().get(0).getProduct().getDescription(), is("banana"));

        assertThat(shoppingCart.get().getItems().get(1).getId().getValue(), is("item2"));
        assertThat(shoppingCart.get().getItems().get(1).getProduct().getBarcode().getValue(), is("barcode2"));
        assertThat(shoppingCart.get().getItems().get(1).getProduct().getUnitPrice(), is(new BigDecimal("5.00")));
        assertThat(shoppingCart.get().getItems().get(1).getQuantity(), is(new BigDecimal("3.00")));
        assertThat(shoppingCart.get().getItems().get(1).getProduct().getDescription(), is("cheese"));
    }

    @Test
    void shouldReturnEmtpyWhenShoppingCartDoesNotExist() {
        Optional<ShoppingCart> shoppingCart = adapterUnderTest.byId(CartId.of("1"));

        assertThat("shoppingCart must be empty", shoppingCart.isEmpty());

    }


    private ShoppingCart mockShoppingCart(String id, Country country) {
        CartItem cartItem = CartItem.of(
            "1",
            "bc",
            BigDecimal.TEN,
            "desc",
            BigDecimal.ONE
        );
        return ShoppingCart.of(
            CartId.of(id), country,
            createdDate, Map.of(cartItem.getId(), cartItem)
        );
    }

}