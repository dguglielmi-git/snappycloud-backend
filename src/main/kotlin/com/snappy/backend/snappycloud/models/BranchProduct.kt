package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class BranchProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @JoinColumn(name = "branch_office_id")
    val branchOffice: BranchOffice,
    @JoinColumn(name = "product_id")
    val product: Product,
    @Column(name = "stock_alert_min")
    val stockAlertMin: Double,
    @Column(name = "stock_return")
    val stockReturn: Double,
    @Column(name = "stock_available")
    val stockAvailable: Double,
    @Column(name = "stock_physical")
    val stockPhysical: Double
)