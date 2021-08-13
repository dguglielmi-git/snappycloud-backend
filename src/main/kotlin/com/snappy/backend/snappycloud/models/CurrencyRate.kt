package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "currency_rate")
data class CurrencyRate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name ="rate_last_update")
    var rateLastUpdate: Date,
    @Column(name = "rate_value")
    var rateValue: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cr_currency_id")
    val currency: Currency
)