package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "branch_product")
data class BranchProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "stock_alert_min")
    var stockAlertMin: Double,
    @Column(name = "stock_return")
    var stockReturn: Double,
    @Column(name = "stock_available")
    var stockAvailable: Double,
    @Column(name = "stock_physical")
    var stockPhysical: Double,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_office_fk")
    val branchOffice: BranchOffice,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bp_product_fk")
    val product: Product
)