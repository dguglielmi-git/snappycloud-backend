package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "profile_name")
    var profileName: String
)