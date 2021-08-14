package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = "profile_name")
    var name: String
)