package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Supplier(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var name: String,
    var address: String?,
    var city: String?,
    var state: String?,
    var zipCode: String?,
    var phone: String?,
    var fax: String?,
    var email: String?,
    var website: String?,
    @Column(name = "mobile_phone")
    var mobilePhone: String?,
    @Column(name = "alternative_phone")
    var alternativePhone: String?,
    @Column(name = "contact_name")
    var contactName: String?,
    @Column(name = "ordering_date")
    var OrderingDate: String?,
    @Column(name = "delivery_days")
    var deliveryDays: String?,
    @Column(name = "delivery_hours")
    var deliveryHours: String?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_business_fk")
    var business: Business
)