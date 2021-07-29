package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val number: Int,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="client_id")
    val client: Client,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_condition_id")
    val saleCondition: SaleCondition,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    val currency: Currency,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrier_id")
    val carrier: Carrier,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    val sale: Sale,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type_id")
    val docType: DocumentType,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remittance_id")
    val remittance: Remittance
)