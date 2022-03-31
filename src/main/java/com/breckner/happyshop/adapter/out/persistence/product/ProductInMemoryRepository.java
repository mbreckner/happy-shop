package com.breckner.happyshop.adapter.out.persistence.product;

import com.breckner.happyshop.domain.model.Barcode;
import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.Product;
import com.breckner.happyshop.domain.model.ShoppingCart;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
class ProductInMemoryRepository {

    //TODO: Price by Currency

    private final List<Product> products = List.of(
        Product.of(Barcode.of("90001"), BigDecimal.valueOf(0.60), "Bananas"),
        Product.of(Barcode.of("90001"), BigDecimal.valueOf(39.9), "Gin Mare")
    );

    public Optional<Product> findByBarcode(Barcode barcode) {
        return products.stream()
            .filter(product -> product.getBarcode().equals(barcode))
            .findFirst();
    }
}
