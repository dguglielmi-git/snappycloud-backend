package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var surname: String,
    var address: String? = "",
    var apartment: String? = "",
    var floor: String? = "",
    var city: String? = "",
    var state: String? = "",
    var zipCode: String? = "",
    var dni: String? = "",
    var email: String? = "",
    var phone: String? = "",
    var mobile: String? = "",
    @Column(name = "mobile_corp")
    var mobileCorp: String? = "",
    @Column(name = "cuit_nino_ssn")
    var cuitNinoSsn: String? = "",
    @Column(name = "cuit_nino_ssn_corp")
    var cuitNinoSsnCorp: String? = "",
    @Column(name = "issue_date_system")
    val issueDateSystem: Calendar,
    @Column(name = "last_update")
    var lastUpdate: Calendar,
    @Column(name = "num_iibb")
    var iibb: String? = "",
    @Column(name = "company_name")
    var companyName: String? = "",
    @Column(name = "balance_last_update")
    var balanceLastUpdate: Calendar,
    @Column(name = "balance_debit")
    var balanceDebit: Double,
    @Column(name = "balance_credit")
    var balanceCredit: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_position_fk")
    var position: Position,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_country_fk")
    var country: Country,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "client_tax_fk")
    var clientTax: List<Tax> = mutableListOf<Tax>(),
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_business_fk")
    val business: Business
)
