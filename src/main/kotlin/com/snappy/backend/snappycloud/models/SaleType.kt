package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "sale_type")
data class SaleType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var description: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_type_business_fk")
    val business: Business
)