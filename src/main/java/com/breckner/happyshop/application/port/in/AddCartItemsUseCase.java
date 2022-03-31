package com.breckner.happyshop.application.port.in;

import com.breckner.happyshop.domain.model.Barcode;
import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.CartItemId;
import lombok.Value;

import java.math.BigDecimal;

public interface AddCartItemsUseCase {

    void addCartItems(AddCartItemsInput input);

    @Value
    class AddCartItemsInput {
        CartId shoppingCartId;
        Barcode barcode;
        CartItemId cartItemId;
        BigDecimal quantity;
    }
}
