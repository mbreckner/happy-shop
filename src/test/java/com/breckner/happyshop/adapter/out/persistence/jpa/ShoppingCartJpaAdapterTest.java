package com.breckner.happyshop.adapter.out.persistence.jpa;

import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.breckner.happyshop.domain.service.DateTimeHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

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

    private final ZonedDateTime createdDate = Instant.parse("2022-03-09T10:15:30Z").atZone(ZoneId.of("UTC"));

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

    }

    @Test
    void shouldUpdateExistingShoppingCart() {
        ShoppingCartJpaEntity existingShoppingCartEntity = new ShoppingCartJpaEntity(
            "1", "CHE", DateTimeHelper.now()
        );
        shoppingCartJpaRepository.save(existingShoppingCartEntity);
        ShoppingCart shoppingCartToUpdate = mockShoppingCart("1", Country.GERMANY);

        adapterUnderTest.persist(shoppingCartToUpdate);

        List<ShoppingCartJpaEntity> shoppingCartJpaEntities = shoppingCartJpaRepository.findAll();
        assertThat(shoppingCartJpaEntities.size(), is(1));
        assertThat(shoppingCartJpaEntities.get(0).getId(), is("1"));
        assertThat(shoppingCartJpaEntities.get(0).getCountryCode(), is("DEU"));
        assertThat(shoppingCartJpaEntities.get(0).getCreatedDate(), is(createdDate));

    }

    @Test
    void shouldLoadExistingShoppingCart() {
        ShoppingCartJpaEntity existingShoppingCartEntity = new ShoppingCartJpaEntity(
            "1", "CHE", createdDate
        );
        shoppingCartJpaRepository.save(existingShoppingCartEntity);

        Optional<ShoppingCart> shoppingCart = adapterUnderTest.byId(CartId.of("1"));

        assertThat("shoppingCart may not be empty", shoppingCart.isPresent());
        assertThat(shoppingCart.get().getId(), is(CartId.of("1")));
        assertThat(shoppingCart.get().getCountry(), is(Country.SWITZERLAND));
        assertThat(shoppingCart.get().getCreatedDate(), is(createdDate));
    }

    @Test
    void shouldReturnEmtpyWhenShoppingCartDoesNotExist() {
        Optional<ShoppingCart> shoppingCart = adapterUnderTest.byId(CartId.of("1"));

        assertThat("shoppingCart must be empty", shoppingCart.isEmpty());

    }


    private ShoppingCart mockShoppingCart(String id, Country country) {
        return ShoppingCart.of(
            CartId.of(id), country,
            createdDate, Map.of()
        );
    }

}