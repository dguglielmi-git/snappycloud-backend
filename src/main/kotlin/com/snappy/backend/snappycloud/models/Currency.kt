package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Currency(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    var symbol: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_business_fk")
    val business: Business
)