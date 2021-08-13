package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.SaleItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SaleItemRepository : JpaRepository<SaleItem, Long>