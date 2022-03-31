package com.breckner.happyshop.adapter.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
class CreateCartController {

    @PostMapping(
        path = "/cart"
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateShoppingCartResponseDto create(@RequestBody @Valid CreateShoppingCartRequestDto request) {
        return new CreateShoppingCartResponseDto(UUID.randomUUID(), request.getTest());
    }

    @Data
    static class CreateShoppingCartRequestDto {
        String test;
    }

    @Data
    @AllArgsConstructor
    static class CreateShoppingCartResponseDto {
        UUID id;
        String test;
    }
}
