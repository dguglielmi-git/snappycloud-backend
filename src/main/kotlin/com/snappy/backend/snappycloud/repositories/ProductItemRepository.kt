package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.ProductItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductItemRepository : JpaRepository<ProductItem, Long>