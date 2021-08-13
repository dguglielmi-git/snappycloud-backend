package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
@Table(name="branch_office")
data class BranchOffice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var address: String?,
    var city: String?,
    var state: String?,
    var active: Int,
    @Column(name = "internal_branch_code")
    var internalBranchCode: String?,
    @Column(name = "start_date")
    val startDate: Date,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bo_country_fk")
    var country: Country?,
)