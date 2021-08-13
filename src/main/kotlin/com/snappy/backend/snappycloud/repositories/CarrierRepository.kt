package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Carrier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarrierRepository : JpaRepository<Carrier, Long>