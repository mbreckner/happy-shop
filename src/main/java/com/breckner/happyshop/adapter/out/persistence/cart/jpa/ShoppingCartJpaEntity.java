package com.breckner.happyshop.adapter.out.persistence.cart.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class ShoppingCartJpaEntity {

    @Id
    private String id;
    private String countryCode;
    private ZonedDateTime createdDate;

}
