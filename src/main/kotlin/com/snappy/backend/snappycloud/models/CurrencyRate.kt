package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class CurrencyRate(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name ="rate_last_update")
    val rateLastUpdate: Date,
    @Column(name = "rate_value")
    val rateValue: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    val currency: Currency
)