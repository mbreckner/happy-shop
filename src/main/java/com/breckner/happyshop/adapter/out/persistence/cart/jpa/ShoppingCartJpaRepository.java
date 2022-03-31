package com.breckner.happyshop.adapter.out.persistence.cart.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCartJpaEntity, String> {
}
