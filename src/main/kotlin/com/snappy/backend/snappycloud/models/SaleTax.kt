package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class SaleTax(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tax_id")
    val tax: Tax
)