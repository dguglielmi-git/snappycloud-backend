package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "payment_method")
data class PaymentMethod(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val description: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_business_fk")
    val business: Business
)