package com.breckner.happyshop.application.service;

import com.breckner.happyshop.application.port.in.AddCartItemsUseCase;
import com.breckner.happyshop.application.port.out.LoadCartPort;
import com.breckner.happyshop.application.port.out.LoadProductPort;
import com.breckner.happyshop.application.port.out.PersistCartPort;
import com.breckner.happyshop.domain.model.*;
import com.breckner.happyshop.domain.model.exception.BusinessRuleException;
import com.breckner.happyshop.domain.model.exception.NotFoundException;
import com.breckner.happyshop.domain.service.DateTimeHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(ValidationAutoConfiguration.class)
class AddCartItemsServiceTest {

    @MockBean private LoadCartPort loadCartPort;
    @MockBean private PersistCartPort persistCartPort;
    @MockBean private LoadProductPort loadProductPort;

    @Autowired
    private AddCartItemsUseCase serviceUnderTest;

    private ShoppingCart shoppingCart = mockShoppingCart("1");

    @Test
    void shouldAddCartItems() {
        Barcode barcode = Barcode.of("barcode");
        Product product = Product.of(barcode, BigDecimal.valueOf(1.50), "Bananas");
        AddCartItemsUseCase.AddCartItemsInput input = new AddCartItemsUseCase.AddCartItemsInput(
            CartId.of("1"), barcode, CartItemId.of("cartItemId"), BigDecimal.valueOf(2.00)
        );
        when(loadCartPort.byId(any())).thenReturn(Optional.of(shoppingCart));
        when(loadProductPort.byBarcode(barcode)).thenReturn(Optional.of(product));

        serviceUnderTest.addCartItems(input);

        ArgumentCaptor<ShoppingCart> shoppingCartArgumentCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        then(persistCartPort).should().persist(shoppingCartArgumentCaptor.capture());
        ShoppingCart shoppingCartArg = shoppingCartArgumentCaptor.getValue();
        assertThat(shoppingCartArg.getItems().size(), is(1));
        assertThat(shoppingCartArg.getItems().get(0).getId(), is(CartItemId.of("cartItemId")));
        assertThat(shoppingCartArg.getItems().get(0).getProduct(), is(product));
        assertThat(shoppingCartArg.getItems().get(0).getQuantity(), is(BigDecimal.valueOf(2.00)));

    }

    @Test
    void shouldThrowBusinessRuleException_WhenShoppingCartDoesNotExist() {
        Barcode barcode = Barcode.of("barcode");
        AddCartItemsUseCase.AddCartItemsInput input = new AddCartItemsUseCase.AddCartItemsInput(
            CartId.of("1"), barcode, CartItemId.of("cartItemId"), BigDecimal.valueOf(2.0)
        );
        when(loadCartPort.byId(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> serviceUnderTest.addCartItems(input));
    }

    @Test
    void shouldThrowBusinessRuleException_WhenProductDoesNotExist() {
        Barcode barcode = Barcode.of("barcode");
        AddCartItemsUseCase.AddCartItemsInput input = new AddCartItemsUseCase.AddCartItemsInput(
            CartId.of("1"), barcode, CartItemId.of("cartItemId"), BigDecimal.valueOf(2.0)
        );
        when(loadCartPort.byId(any())).thenReturn(Optional.of(shoppingCart));
        when(loadProductPort.byBarcode(barcode)).thenReturn(Optional.empty());

        Assertions.assertThrows(BusinessRuleException.class, () -> serviceUnderTest.addCartItems(input));

    }


    private ShoppingCart mockShoppingCart(String id) {
        return ShoppingCart.of(CartId.of(id), Country.SWITZERLAND, DateTimeHelper.now(), new HashMap<>());
    }

}