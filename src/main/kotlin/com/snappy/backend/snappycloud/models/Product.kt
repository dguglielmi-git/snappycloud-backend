package com.snappy.backend.snappycloud.models

import java.util.*
import javax.persistence.*

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var barcode: String?,
    var description: String,
    @Column(name = "total_cost")
    var totalCost: Double,
    @Column(name = "short_description")
    var shortDescription: String,
    @Column(name = "flag_percentage")
    var flagPercentage: Int?,
    @Column(name = "img_product")
    var imgProduct: String?,
    @Column(name = "percentage_earning_expected")
    var percentageEarningExpected: Double?,
    @Column(name = "percentage_earning_wholesale")
    var percentageEarningWholesale: Double?,
    @Column(name = "percentage_earning_retail")
    var percentageEarningRetail: Double?,
    @Column(name = "sale_price_special")
    var salePriceSpecial: Double,
    @Column(name = "sale_price_wholesale")
    var salePriceWholesale: Double,
    @Column(name = "sale_price_retail")
    var salePriceRetail: Double,
    @Column(name = "price_last_update")
    var priceLastUpdate: Date,
    @Column(name = "product_last_update")
    var productLastUpdate: Date,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_sales_type_fk")
    var saleTypeDefault: SaleType,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_main_supplier_fk")
    var mainSupplier: Supplier,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_business_fk")
    val business: Business,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_category_fk")
    var category: Category,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prod_brand_fk")
    var brand: Brand,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_item_fk")
    val productItems: List<ProductItem> = mutableListOf<ProductItem>()
)