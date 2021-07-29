package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Sale(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val invoice: Int,
    val subtotal: Double,
    @Column(name = "issue_date")
    val issueDate: Date,
    @Column(name = "subtotal_taxes")
    val subtotalTaxes: Double,
    @Column(name = "total_sale")
    val totalSale: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    val currency: Currency,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_office_id")
    val branchOffice: BranchOffice,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    val paymentMethod: PaymentMethod,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "sale_tax_fk")
    val saleTax: List<SaleTax> = mutableListOf<SaleTax>(),
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val saleItem: List<SaleItem> = mutableListOf<SaleItem>()
)