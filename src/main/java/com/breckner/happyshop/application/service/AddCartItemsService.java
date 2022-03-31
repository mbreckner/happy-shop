package com.breckner.happyshop.application.service;

import com.breckner.happyshop.application.port.in.AddCartItemsUseCase;
import com.breckner.happyshop.application.port.out.LoadProductPort;
import com.breckner.happyshop.application.port.out.LoadCartPort;
import com.breckner.happyshop.application.port.out.PersistCartPort;
import com.breckner.happyshop.domain.model.CartItem;
import com.breckner.happyshop.domain.model.CartItemId;
import com.breckner.happyshop.domain.model.Product;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.breckner.happyshop.domain.model.exception.BusinessRuleException;
import com.breckner.happyshop.domain.model.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddCartItemsService implements AddCartItemsUseCase {

    private final LoadCartPort loadCartPort;
    private final PersistCartPort persistCartPort;
    private final LoadProductPort loadProductPort;

    @Override
    public void addCartItems(AddCartItemsInput input) {

        ShoppingCart shoppingCart = loadCartPort.byId(input.getShoppingCartId())
            .orElseThrow(() -> new NotFoundException("shopping cart"));
        Product product = loadProductPort.byBarcode(input.getBarcode())
            .orElseThrow(() -> new BusinessRuleException("no product with this barcode exists"));

        CartItem cartItem = CartItem.of(input.getCartItemId(), product, input.getQuantity());
        shoppingCart.addItems(List.of(cartItem));

        persistCartPort.persist(shoppingCart);

    }
}
