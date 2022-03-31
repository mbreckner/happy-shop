package com.breckner.happyshop.adapter.in.web;

import com.breckner.happyshop.adapter.in.web.validator.ValidCountryCode;
import com.breckner.happyshop.application.port.in.CreateCartUseCase;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
class CreateCartController {

    private final CreateCartUseCase createCartUseCase;

    @PostMapping(
        path = "/cart",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateCartResponseDto create(@RequestBody @Valid CreateCartRequestDto request) {

        Country country = Country.byCode(request.getCountryCode());
        ShoppingCart shoppingCart = createCartUseCase.create(new CreateCartUseCase.CreateCartInput(country));

        return new CreateCartResponseDto(shoppingCart.getId().getValue());
    }

    @Data
    static class CreateCartRequestDto {
        @ValidCountryCode
        @JsonProperty("country_code")
        private String countryCode;
    }

    @Data
    @AllArgsConstructor
    static class CreateCartResponseDto {
        String id;
    }
}
