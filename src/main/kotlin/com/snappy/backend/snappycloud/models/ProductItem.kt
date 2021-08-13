package com.snappy.backend.snappycloud.models

import javax.persistence.*

/**
 * Some products are not final products, but are products made
 * with different materials.
 * A product item includes the material and the quantity of it,
 * which ends up being part of the final product.
 *
 * The final price of a manufactured product comes from the sum of all
 * the costs of every item.
 */

@Entity
@Table(name = "product_item")
data class ProductItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var quantity: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proditem_material_fk")
    var material: Material
) {
    fun getCost() = material.unitPrice * quantity
}