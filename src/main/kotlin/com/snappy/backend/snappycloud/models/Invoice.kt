package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val number: Int,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="invoice_client_fk")
    var client: Client?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_currency_fk")
    var currency: Currency,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_carrier_fk")
    var carrier: Carrier?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_doc_type_fk")
    var docType: DocumentType,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_remittance_fk")
    var remittance: Remittance?
)