package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class RemittanceItem(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,
    @Column(name = "unit_price")
    val unitPrice: Double,
    val quantity: Int,
    val total: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_type_id")
    val saleType: SaleType
)