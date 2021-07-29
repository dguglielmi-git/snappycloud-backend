package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class SaleItem(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,
    @Column(name = "unit_price")
    val unitPrice: Double,
    val quantity: Int,
    @Column(name = "total_price")
    val totalPrice: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_type_id")
    val saleType: SaleType
)