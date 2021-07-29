package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Remittance(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column(name ="issue_date")
    val issueDate: Date,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    val client: Client,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_condition_id")
    val salesCondition: SaleCondition,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_office_id")
    val branchOffice: BranchOffice,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "remittance_fk")
    val remittanceItem: List<RemittanceItem> = mutableListOf<RemittanceItem>()
)