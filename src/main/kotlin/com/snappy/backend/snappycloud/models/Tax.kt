package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Tax(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var description: String,
    var percentage: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_business_fk")
    val business: Business
)