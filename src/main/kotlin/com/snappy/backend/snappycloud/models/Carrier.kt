package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Carrier(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var surname: String,
    var phone: String?,
    var company: String?,
    @Column(name = "cuit_nino_ssn")
    var cuitNinoSsn: String,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_business_fk")
    val business: Business,
)