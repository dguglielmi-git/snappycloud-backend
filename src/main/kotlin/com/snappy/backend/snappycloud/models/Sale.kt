package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Sale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val subtotal: Double,
    @Column(name = "issue_date")
    val issueDate: Calendar,
    @Column(name = "subtotal_taxes")
    val subtotalTaxes: Double,
    @Column(name = "total_sale")
    val totalSale: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_user_fk")
    val user: User,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_invoice_fk")
    var invoice: Invoice?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_currency_fk")
    val currency: Currency,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_branch_office_fk")
    val branchOffice: BranchOffice,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_payment_method_fk")
    val paymentMethod: PaymentMethod,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "sale_tax_fk")
    val saleTax: List<SaleTax> = mutableListOf<SaleTax>(),
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "sale_sale_item_fk")
    val saleItem: List<SaleItem> = mutableListOf<SaleItem>(),
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_business_fk")
    val business: Business
)