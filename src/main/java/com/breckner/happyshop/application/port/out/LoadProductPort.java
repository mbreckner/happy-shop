package com.breckner.happyshop.application.port.out;

import com.breckner.happyshop.domain.model.Barcode;
import com.breckner.happyshop.domain.model.Product;

import java.util.Optional;

public interface LoadProductPort {

    Optional<Product> byBarcode(Barcode barcode);

}
