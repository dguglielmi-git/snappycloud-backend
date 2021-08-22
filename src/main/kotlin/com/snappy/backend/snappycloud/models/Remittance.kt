package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Remittance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name ="issue_date")
    val issueDate: Calendar,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    var client: Client,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remittance_branch_office_fk")
    var branchOffice: BranchOffice,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "remittance_items_fk")
    val remittanceItem: List<RemittanceItem> = mutableListOf<RemittanceItem>(),
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remittance_business_fk")
    val business: Business
)