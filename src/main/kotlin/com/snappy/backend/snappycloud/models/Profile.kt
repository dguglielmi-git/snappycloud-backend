package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name = "profile_name")
    val profileName: String,
    @OneToOne
    @JoinColumn(name = "business_id")
    val business: Business,
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "functions_profile",
        joinColumns = [JoinColumn(name = "fk_profile")],
        inverseJoinColumns = [JoinColumn(name = "fk_function")]
    )
    val functions: List<Function> = mutableListOf<Function>(),
)