package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Sale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SaleRepository : JpaRepository<Sale, Long>