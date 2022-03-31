package com.breckner.happyshop.application.port.in;

import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface CreateCartUseCase {

    ShoppingCart create(@NotNull @Valid CreateCartInput input);

    @Value
    class CreateCartInput {
        @NotNull Country country;
    }
}
