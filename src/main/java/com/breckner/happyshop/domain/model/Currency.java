package com.breckner.happyshop.domain.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Currency {

    CHF("CHF", "Swiss franc"),
    EUR("EUR", "Euro");

    // ISO-4217
    public final String code;
    public final String displayName;
}
