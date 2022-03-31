package com.breckner.happyshop.adapter.out.persistence.cart.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity(name = "CartItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
class CartItemJpaEntity {

    private static final String TECHNICAL_ID_SEPERATOR = "::";

    @Id
    private String id;

    private String cartItemId;
    private String barcode;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private String description;

    public CartItemJpaEntity(
        String cartItemId,
        String barcode,
        BigDecimal quantity,
        BigDecimal unitPrice,
        String description,
        ShoppingCartJpaEntity shoppingCart
    ) {
        this.id = shoppingCart.getId().concat(TECHNICAL_ID_SEPERATOR).concat(cartItemId);
        this.cartItemId = cartItemId;
        this.barcode = barcode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.shoppingCart = shoppingCart;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoppingCartId", referencedColumnName = "id")
    ShoppingCartJpaEntity shoppingCart;

}
