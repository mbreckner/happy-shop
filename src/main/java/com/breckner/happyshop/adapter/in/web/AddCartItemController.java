package com.breckner.happyshop.adapter.in.web;

import com.breckner.happyshop.application.port.in.AddCartItemsUseCase;
import com.breckner.happyshop.application.port.in.CreateCartUseCase;
import com.breckner.happyshop.domain.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
@Validated
class AddCartItemController {

    private final AddCartItemsUseCase addCartItemsUseCase;

    @PostMapping(
        path = "/cart/{cartId}/items",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(
        @PathVariable @NotBlank String cartId,
        @RequestBody @Valid AddCartItemRequestDto request) {

        AddCartItemsUseCase.AddCartItemsInput input = new AddCartItemsUseCase.AddCartItemsInput(
            CartId.of(cartId),
            Barcode.of(request.getBarcode()),
            CartItemId.of(request.getCartItemId()),
            request.getQuantity()
        );

        addCartItemsUseCase.addCartItems(input);
    }

    @Data
    @EqualsAndHashCode
    static class AddCartItemRequestDto {
        @NotBlank private String barcode;
        @NotBlank private String cartItemId;
        @NotNull private BigDecimal quantity;
    }
}
