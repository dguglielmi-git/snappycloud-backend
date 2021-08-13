package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Tax
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaxRepository : JpaRepository<Tax, Long>