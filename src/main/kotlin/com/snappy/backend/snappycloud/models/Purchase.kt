package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Purchase(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val quantity: Double,
    @Column(name = "current_cost")
    val currentCost: Double,
    @Column(name = "last_cost")
    val lastCost: Double,
    @Column(name = "purchase_date")
    val purchaseDate: Date,
    @Column(name = "last_stock")
    val lastStock: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    val material: Material,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product
)