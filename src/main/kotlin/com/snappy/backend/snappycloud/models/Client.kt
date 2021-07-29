package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val surname: String,
    val address: String,
    val apartment: String?,
    val floor: String?,
    val city: String,
    val state: String,
    val zipCode: String,
    val dni: String?,
    val email: String?,
    val phone: String?,
    val mobile: String?,
    @Column(name = "mobile_corp")
    val mobileCorp: String?,
    @Column(name = "cuit_nino_ssn")
    val cuitNinoSsn: String?,
    @Column(name = "cuit_nino_ssn_corp")
    val cuitNinoSsnCorp: String?,
    @Column(name = "issue_date_system")
    val issueDateSystem: Date,
    @Column(name = "last_update")
    val lastUpdate: Date,
    @Column(name = "num_iibb")
    val iibb: String?,
    @Column(name = "company_name")
    val companyName: String?,
    @Column(name = "balance_last_update")
    val balanceLastUpdate: Date,
    @Column(name = "balance_debit")
    val balanceDebit: Double,
    @Column(name = "balance_credit")
    val balanceCredit: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    val position: Position,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    val country: Country,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vat_condition_id")
    val vatCondition: VatCondition,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_currency_id")
    val clientCurrency: Currency,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "client_tax_fk")
    val clientTax: List<Tax> = mutableListOf<Tax>()
)
