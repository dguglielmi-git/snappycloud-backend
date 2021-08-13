package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Brand(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val description: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_business_fk")
    val business: Business
)