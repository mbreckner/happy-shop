package com.breckner.happyshop.adapter.in.web;

import com.breckner.happyshop.domain.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCartResponseDtoMapper {

    public GetCartController.GetCartResponseDto toResponseDto(ShoppingCart shoppingCart) {
        return new GetCartController.GetCartResponseDto(
            shoppingCart.getId().getValue(),
            shoppingCart.getTotalPrice(),
            shoppingCart.getCountry().code,
            shoppingCart.getCountry().displayName,
            shoppingCart.getCreatedDate(),
            shoppingCart.getCountry().currency.code,
            List.of()
        );
    }
}
