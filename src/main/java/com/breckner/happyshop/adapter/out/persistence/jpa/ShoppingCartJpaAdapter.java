package com.breckner.happyshop.adapter.out.persistence.jpa;

import com.breckner.happyshop.application.port.out.LoadCartPort;
import com.breckner.happyshop.application.port.out.PersistCartPort;
import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ShoppingCartJpaAdapter implements PersistCartPort, LoadCartPort {

    private final ShoppingCartJpaRepository shoppingCartJpaRepository;
    private final ShoppingCartEntityMapper shoppingCartEntityMapper;

    @Override
    public void persist(ShoppingCart shoppingCart) {
        shoppingCartJpaRepository.save(shoppingCartEntityMapper.toEntity(shoppingCart));
    }

    @Override
    public Optional<ShoppingCart> byId(CartId id) {
        return shoppingCartJpaRepository.findById(id.getValue())
            .map(shoppingCartEntityMapper::toDomainObject);
    }
}
