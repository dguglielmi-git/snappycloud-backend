package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Business(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var description: String?,
    var active: Int,
    @Column(name = "start_date")
    val startDate: Date,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "business_bo_fk")
    var branchOffice: List<BranchOffice> = mutableListOf<BranchOffice>(),
    @OneToMany(fetch = FetchType.LAZY, cascade= [CascadeType.ALL])
    @JoinColumn(name = "business_profile_fk")
    var profile: List<Profile> = mutableListOf<Profile>()
)