package com.breckner.happyshop.adapter.out.persistence.product;

import com.breckner.happyshop.application.port.out.LoadProductPort;
import com.breckner.happyshop.domain.model.Barcode;
import com.breckner.happyshop.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductInMemoryAdapter implements LoadProductPort {

    private final ProductInMemoryRepository productInMemoryRepository;

    @Override
    public Optional<Product> byBarcode(Barcode barcode) {
        return productInMemoryRepository.findByBarcode(barcode);
    }
}
