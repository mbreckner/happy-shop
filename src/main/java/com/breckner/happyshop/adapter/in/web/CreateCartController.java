package com.breckner.happyshop.adapter.in.web;

import com.breckner.happyshop.application.port.in.CreateCartUseCase;
import com.breckner.happyshop.domain.model.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
class CreateCartController {

    private final CreateCartUseCase createCartUseCase;

    @PostMapping(
        path = "/cart"
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateShoppingCartResponseDto create(@RequestBody @Valid CreateShoppingCartRequestDto request) {

        ShoppingCart shoppingCart = createCartUseCase.create(null);

        return new CreateShoppingCartResponseDto(UUID.randomUUID(), request.getTest());
    }

    @Data
    static class CreateShoppingCartRequestDto {
        @NotNull String test;
    }

    @Data
    @AllArgsConstructor
    static class CreateShoppingCartResponseDto {
        UUID id;
        String test;
    }
}
