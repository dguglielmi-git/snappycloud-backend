package com.snappy.backend.snappycloud.models

import org.hibernate.annotations.Fetch
import java.util.*
import javax.persistence.*

@Entity
data class Material(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var description: String,
    @Column(name = "unit_quantity")
    var unitQuantity: Double,
    @Column(name = "unit_price")
    var unitPrice: Double,
    @Column(name = "stock_alert")
    var stockAlert: Double,
    @Column(name = "stock_expected")
    var stockExpected: Double,
    @Column(name = "stock_available")
    var stockAvailable: Double,
    @Column(name = "last_update")
    var lastUpdate: Date,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_business_fk")
    val business: Business,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_measure_fk")
    var measure: UnitMeasure
)