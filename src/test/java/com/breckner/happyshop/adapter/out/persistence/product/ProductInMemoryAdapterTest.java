package com.breckner.happyshop.adapter.out.persistence.product;

import com.breckner.happyshop.domain.model.Barcode;
import com.breckner.happyshop.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

class ProductInMemoryAdapterTest {

    private final ProductInMemoryRepository productInMemoryRepository = new ProductInMemoryRepository();
    private final ProductInMemoryAdapter adapterUnderTest = new ProductInMemoryAdapter(productInMemoryRepository);

    @Test
    void shouldLoadProduct() {
        Optional<Product> product = adapterUnderTest.byBarcode(Barcode.of("90001"));

        assertThat("is present", product.isPresent());
    }

    @Test
    void shouldReturnEmpty_WhenProductDoesNotExist() {
        Optional<Product> product = adapterUnderTest.byBarcode(Barcode.of("xxx"));

        assertThat("is empty", product.isEmpty());
    }

}