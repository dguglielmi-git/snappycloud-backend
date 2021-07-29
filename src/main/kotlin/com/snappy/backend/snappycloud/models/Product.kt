package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val barcode: String?,
    val description: String,
    @Column(name = "total_cost")
    val totalCost: Double,
    @Column(name = "short_description")
    val shortDescription: String,
    @Column(name = "flag_percentage")
    val flagPercentage: Int?,
    @Column(name = "img_product")
    val imgProduct: String?,
    @Column(name ="percentage_earning_expected")
    val percentageEarningExpected: Double,
    @Column(name ="percentage_earning_wholesale")
    val percentageEarningWholesale: Double,
    @Column(name ="percentage_earning_retail")
    val percentageEarningRetail: Double,
    @Column(name = "sale_price_special")
    val salePriceSpecial: Double,
    @Column(name = "sale_price_wholesale")
    val salePriceWholesale: Double,
    @Column(name = "sale_price_retail")
    val salePriceRetail: Double,
    @Column(name = "price_last_update")
    val priceLastUpdate: Date,
    @Column(name = "product_last_update")
    val productLastUpdate: Date,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_type_default")
    val saleTypeDefault: SaleType,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_supplier")
    val mainSupplier: Supplier,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    val business: Business,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    val brand: Brand,
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_item_fk")
    val productItems: List<ProductItem> = mutableListOf<ProductItem>(),


    )