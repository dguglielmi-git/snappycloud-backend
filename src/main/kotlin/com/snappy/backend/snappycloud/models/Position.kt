package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Position(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var description: String,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_business_fk")
    val business: Business
)