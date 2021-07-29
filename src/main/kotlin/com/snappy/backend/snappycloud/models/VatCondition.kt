package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class VatCondition(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val description: String,
    @Column(name = "tax_perception")
    val taxPerception: Double
)