package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Function(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "function_code")
    val functionCode: String
) {
}