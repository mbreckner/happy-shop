package com.breckner.happyshop.application.service;

import com.breckner.happyshop.application.port.in.GetCartUseCase;
import com.breckner.happyshop.application.port.out.LoadCartPort;
import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.breckner.happyshop.domain.model.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(ValidationAutoConfiguration.class)
class GetCartServiceTest {

    @MockBean LoadCartPort loadCartPort;
    @Autowired GetCartUseCase getCartUseCase;

    @Test
    void shouldLoadCart() {
        ShoppingCart mockShoppingCart = ShoppingCart.create(Country.SWITZERLAND);
        CartId cartId = CartId.of("1");
        when(loadCartPort.byId(any())).thenReturn(Optional.of(mockShoppingCart));

        ShoppingCart result = getCartUseCase.get(cartId);

        assertThat(result, is(mockShoppingCart));
        then(loadCartPort).should().byId(cartId);

    }

    @Test
    void shouldThrowNotFoundException_WhenCartDoesNotExist() {
        CartId cartId = CartId.of("1");
        when(loadCartPort.byId(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> getCartUseCase.get(cartId));

    }

}