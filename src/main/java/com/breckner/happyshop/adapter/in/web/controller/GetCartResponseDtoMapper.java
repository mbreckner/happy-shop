package com.breckner.happyshop.adapter.in.web.controller;

import com.breckner.happyshop.domain.model.CartItem;
import com.breckner.happyshop.domain.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class GetCartResponseDtoMapper {

    public GetCartController.GetCartResponseDto toResponseDto(ShoppingCart shoppingCart) {
        return new GetCartController.GetCartResponseDto(
            shoppingCart.getId().getValue(),
            shoppingCart.getTotalPrice(),
            shoppingCart.getCountry().code,
            shoppingCart.getCountry().displayName,
            shoppingCart.getCreatedDate(),
            shoppingCart.getCountry().currency.code,
            toCartItemDtoList(shoppingCart.getItems())
        );
    }

    private List<GetCartController.CartItemDto> toCartItemDtoList(List<CartItem> cartItems) {
        return cartItems.stream()
            .map(this::toCartItemDto)
            .collect(Collectors.toList());
    }

    private GetCartController.CartItemDto toCartItemDto(CartItem cartItem) {
        return new GetCartController.CartItemDto(
            cartItem.getId().getValue(),
            new GetCartController.ProductDto(
                cartItem.getProduct().getBarcode().getValue(),
                cartItem.getProduct().getUnitPrice(),
                cartItem.getProduct().getDescription()),
            cartItem.getQuantity()
        );
    }
}
