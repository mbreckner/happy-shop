package com.breckner.happyshop.adapter.out.persistence.cart.jpa;

import com.breckner.happyshop.domain.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class ShoppingCartEntityMapper {

    public ShoppingCartJpaEntity toEntity(ShoppingCart shoppingCart) {
        ShoppingCartJpaEntity shoppingCartJpaEntity = new ShoppingCartJpaEntity(
            shoppingCart.getId().getValue(),
            shoppingCart.getCountry().code,
            shoppingCart.getCreatedDate()
        );
        List<CartItemJpaEntity> cartItemJpaEntityList = toCartItemEntityList(shoppingCart.getItems(), shoppingCartJpaEntity);
        shoppingCartJpaEntity.setItems(cartItemJpaEntityList);

        return shoppingCartJpaEntity;
    }

    private List<CartItemJpaEntity> toCartItemEntityList(List<CartItem> cartItems, ShoppingCartJpaEntity shoppingCartJpaEntity) {
        return cartItems.stream()
            .map(cartItem -> toCartItemEntity(cartItem, shoppingCartJpaEntity))
            .collect(Collectors.toList());
    }

    private CartItemJpaEntity toCartItemEntity(CartItem cartItem, ShoppingCartJpaEntity shoppingCartJpaEntity) {
        return new CartItemJpaEntity(
            cartItem.getId().getValue(), cartItem.getProduct().getBarcode().getValue(),
            cartItem.getQuantity(), cartItem.getProduct().getUnitPrice(),
            cartItem.getProduct().getDescription(), shoppingCartJpaEntity
        );
    }

    public ShoppingCart toDomainObject(ShoppingCartJpaEntity shoppingCartJpaEntity) {
        return ShoppingCart.of(
            CartId.of(shoppingCartJpaEntity.getId()),
            Country.byCode(shoppingCartJpaEntity.getCountryCode()),
            shoppingCartJpaEntity.getCreatedDate(),
            toCartItemDomainMap(shoppingCartJpaEntity.getItems())
        );
    }

    private Map<CartItemId, CartItem> toCartItemDomainMap(List<CartItemJpaEntity> cartItemJpaEntityList) {
        return cartItemJpaEntityList.stream()
            .map(this::toCartItemDomainObject)
            .collect(Collectors.toMap(CartItem::getId, cartItem -> cartItem));
    }

    private CartItem toCartItemDomainObject(CartItemJpaEntity cartItemJpaEntity) {
        return CartItem.of(
            CartItemId.of(cartItemJpaEntity.getCartItemId()),
            Product.of(
                Barcode.of(cartItemJpaEntity.getBarcode()),
                cartItemJpaEntity.getUnitPrice(),
                cartItemJpaEntity.getDescription()
            ),
            cartItemJpaEntity.getQuantity()
        );
    }

}
