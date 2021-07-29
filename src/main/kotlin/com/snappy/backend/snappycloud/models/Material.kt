package com.snappy.backend.snappycloud.models

import org.hibernate.annotations.Fetch
import java.util.*
import javax.persistence.*

@Entity
data class Material(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val description: String,
    @Column(name = "unit_quantity")
    val unitQuantity: Double,
    @Column(name = "unit_price")
    val unitPrice: Double,
    @Column(name = "stock_alert")
    val stockAlert: Double,
    @Column(name = "stock_expected")
    val stockExpected: Double,
    @Column(name = "stock_available")
    val stockAvailable: Double,
    @Column(name = "last_update")
    val lastUpdate: Date,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    val business: Business,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "measure_id")
    val measure: UnitMeasure
)