package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "sale_item")
data class SaleItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "unit_price")
    val unitPrice: Double,
    val quantity: Int,
    @Column(name = "total_price")
    val totalPrice: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_item_product_fk")
    val product: Product,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_item_sale_type_fk")
    val saleType: SaleType
)