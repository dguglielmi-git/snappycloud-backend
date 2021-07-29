package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
data class Supplier(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val address: String?,
    val city: String?,
    val state: String?,
    val zipCode: String?,
    val phone: String?,
    val fax: String?,
    val email: String?,
    val website: String?,
    @Column(name = "mobile_phone")
    val mobilePhone: String?,
    @Column(name = "alternative_phone")
    val alternativePhone: String?,
    @Column(name = "contact_name")
    val contactName: String?,
    @Column(name = "ordering_date")
    val OrderingDate: String?,
    @Column(name = "delivery_days")
    val deliveryDays: String?,
    @Column(name = "delivery_hours")
    val deliveryHours: String?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    val business: Business
)