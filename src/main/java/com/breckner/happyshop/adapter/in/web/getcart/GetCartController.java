package com.breckner.happyshop.adapter.in.web.getcart;

import com.breckner.happyshop.adapter.in.web.getcart.GetCartResponseDtoMapper;
import com.breckner.happyshop.application.port.in.GetCartUseCase;
import com.breckner.happyshop.domain.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
class GetCartController {

    private final GetCartUseCase getCartUseCase;
    private final GetCartResponseDtoMapper getCartResponseDtoMapper;

    @GetMapping(
        path = "/cart/{cartId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(value = HttpStatus.OK)
    public GetCartResponseDto create(@PathVariable @NotBlank String cartId) {

        ShoppingCart shoppingCart = getCartUseCase.get(CartId.of(cartId));
        return getCartResponseDtoMapper.toResponseDto(shoppingCart);
    }

    @Data
    @AllArgsConstructor
    static class GetCartResponseDto {
        String id;
        @JsonProperty("total_price") BigDecimal totalPrice;
        @JsonProperty("country_code") String countryCode;
        @JsonProperty("country_name") String countryName;
        ZonedDateTime created;
        String currency;
        List<CartItemDto> cartItems;
    }

    @Data
    @AllArgsConstructor
    static class CartItemDto {
        private String id;
        private ProductDto product;
        private BigDecimal quantity;
    }

    @Data
    @AllArgsConstructor
    static class ProductDto {
        private String barcode;
        private BigDecimal unitPrice;
        private String description;
    }
}

