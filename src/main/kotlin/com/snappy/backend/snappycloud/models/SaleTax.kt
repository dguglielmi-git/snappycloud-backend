package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "sale_tax")
data class SaleTax(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_tax_tax_fk")
    val tax: Tax
)