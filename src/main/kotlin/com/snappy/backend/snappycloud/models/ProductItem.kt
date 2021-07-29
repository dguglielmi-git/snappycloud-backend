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
data class ProductItem(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val quantity: Double,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    val material: Material
) {
    fun getCost() = material.unitPrice * quantity
}