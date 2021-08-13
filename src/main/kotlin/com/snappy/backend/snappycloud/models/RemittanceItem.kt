package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "remittance_item")
data class RemittanceItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "unit_price")
    var unitPrice: Double,
    var quantity: Int,
    var total: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remittance_item_product_fk")
    var product: Product,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remittance_sale_type_fk")
    var saleType: SaleType
)