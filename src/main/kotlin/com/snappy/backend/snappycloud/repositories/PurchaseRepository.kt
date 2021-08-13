package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository : JpaRepository<Purchase, Long>