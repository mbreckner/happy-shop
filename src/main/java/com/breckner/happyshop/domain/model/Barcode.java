package com.breckner.happyshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value(staticConstructor = "of")
@EqualsAndHashCode
public class Barcode {

    String value;
}
