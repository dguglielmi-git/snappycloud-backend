package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class BranchOffice(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val active: Int,
    @Column(name = "internal_branch_code")
    val internalBranchCode: String,
    @Column(name = "start_date")
    val startDate: Date,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    val country: Country,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    val business: Business
)