package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Remittance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RemittanceRepository : JpaRepository<Remittance, Long>