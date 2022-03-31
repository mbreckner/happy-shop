package com.breckner.happyshop.application.service;

import com.breckner.happyshop.application.port.in.GetCartUseCase;
import com.breckner.happyshop.application.port.out.LoadCartPort;
import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.breckner.happyshop.domain.model.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCartService implements GetCartUseCase {

    private final LoadCartPort loadCartPort;

    @Override
    public ShoppingCart get(CartId id) {

        return loadCartPort.byId(id)
            .orElseThrow(() -> new NotFoundException("cart"));
    }
}
