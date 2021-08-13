package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var description: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_business_fk")
    val business: Business
)