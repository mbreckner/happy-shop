package com.breckner.happyshop.adapter.out.persistence.cart.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "shoppingCart")
@Data
@NoArgsConstructor
@AllArgsConstructor
class ShoppingCartJpaEntity {

    @Id
    private String id;
    private String countryCode;
    private ZonedDateTime createdDate;

    public ShoppingCartJpaEntity(String id, String countryCode, ZonedDateTime createdDate) {
        this.id = id;
        this.countryCode = countryCode;
        this.createdDate = createdDate;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shoppingCartId", referencedColumnName = "id")
    //@Fetch(value = FetchMode.SELECT)
    private List<CartItemJpaEntity> items = new ArrayList<>();

}
