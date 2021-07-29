package com.snappy.backend.snappycloud.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class UnitMeasure(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val code: String,
    val description: String
)