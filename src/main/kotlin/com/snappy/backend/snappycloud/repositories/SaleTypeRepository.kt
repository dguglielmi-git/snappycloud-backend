package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.SaleType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SaleTypeRepository : JpaRepository<SaleType, Long>