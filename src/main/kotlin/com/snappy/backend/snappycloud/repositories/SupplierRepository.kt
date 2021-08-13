package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Supplier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SupplierRepository : JpaRepository<Supplier, Long>