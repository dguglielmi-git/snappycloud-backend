package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Country(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "country_name")
    var countryName: String
)