package com.breckner.happyshop.adapter.in.web.controller;

import com.breckner.happyshop.domain.model.CartItem;
import com.breckner.happyshop.domain.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GetCartResponseDtoMapper {

    GetCartResponseDtoMapper INSTANCE = Mappers.getMapper(GetCartResponseDtoMapper.class);

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "country.code", target = "countryCode")
    @Mapping(source = "country.displayName", target = "countryName")
    @Mapping(source = "country.currency.code", target = "currency")
    @Mapping(source = "items", target = "cartItems", qualifiedByName = "toCartItemDto")
    GetCartController.GetCartResponseDto toResponseDto(ShoppingCart shoppingCart);

    @Named("toCartItemDto")
    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "product.barcode.value", target = "product.barcode")
    GetCartController.CartItemDto toCartItemDto(CartItem cartItem);
}
