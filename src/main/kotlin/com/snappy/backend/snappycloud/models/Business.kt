package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Business(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val description: String?,
    val active: Int,
    @Column(name = "start_date")
    val startDate: Date
)