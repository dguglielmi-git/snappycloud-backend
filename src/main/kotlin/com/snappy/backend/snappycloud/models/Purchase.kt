package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Purchase(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val quantity: Double,
    @Column(name = "current_cost")
    val currentCost: Double,
    @Column(name = "last_cost")
    val lastCost: Double,
    @Column(name = "purchase_date")
    val purchaseDate: Calendar,
    @Column(name = "last_stock")
    val lastStock: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_user_fk")
    val user: User,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_material_fk")
    val material: Material,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_product_fk")
    val product: Product
)