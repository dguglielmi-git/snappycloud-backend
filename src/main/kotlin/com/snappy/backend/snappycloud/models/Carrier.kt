package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Carrier(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val surname: String,
    val phone: String,
    val company: String?,
    @Column(name ="cuit_nino_ssn")
    val cuitNinoSsn: String
)