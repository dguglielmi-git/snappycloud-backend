package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "unit_measure")
data class UnitMeasure(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var code: String,
    var description: String
)