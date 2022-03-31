package com.breckner.happyshop.adapter.out.persistence.jpa;

import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ShoppingCartEntityMapper {

    public ShoppingCartJpaEntity toEntity(ShoppingCart shoppingCart) {
        return new ShoppingCartJpaEntity(
            shoppingCart.getId().getValue(),
            shoppingCart.getCountry().code,
            shoppingCart.getCreatedDate()
        );
    }

    public ShoppingCart toDomainObject(ShoppingCartJpaEntity jpaEntity) {
        return ShoppingCart.of(
            CartId.of(jpaEntity.getId()),
            Country.byCode(jpaEntity.getCountryCode()),
            jpaEntity.getCreatedDate(),
            List.of()
        );
    }

}
