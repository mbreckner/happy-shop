package com.breckner.happyshop.application.service;

import com.breckner.happyshop.application.port.in.CreateCartUseCase;
import com.breckner.happyshop.application.port.out.PersistCartPort;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintDeclarationException;

import static org.mockito.BDDMockito.then;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(ValidationAutoConfiguration.class)
class CreateCartServiceTest {

    @MockBean
    private PersistCartPort persistCartPort;

    @Autowired
    private CreateCartUseCase createCartUseCase;

    @Test
    void shouldCreateShoppingCart() {
        CreateCartUseCase.CreateCartInput input = new CreateCartUseCase.CreateCartInput(Country.SWITZERLAND);

        ShoppingCart shoppingCart = createCartUseCase.create(input);

        Assertions.assertNotNull(shoppingCart);
        then(persistCartPort).should().persist(shoppingCart);
    }

    @Test
    void shouldThrowConstraintDeclarationException_WhenInputIsInvalid() {
        CreateCartUseCase.CreateCartInput input = new CreateCartUseCase.CreateCartInput(null);

        Assertions.assertThrows(ConstraintDeclarationException.class, () -> createCartUseCase.create(input));
    }

}