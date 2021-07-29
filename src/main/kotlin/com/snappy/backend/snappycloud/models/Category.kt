package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val description: String,
    @JoinColumn(name = "business_id")
    val business: Business
)