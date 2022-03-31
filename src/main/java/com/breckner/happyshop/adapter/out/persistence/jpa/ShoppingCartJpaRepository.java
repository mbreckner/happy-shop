package com.breckner.happyshop.adapter.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

interface ShoppingCartJpaRepository extends JpaRepository<ShoppingCartJpaEntity, String> {
}
