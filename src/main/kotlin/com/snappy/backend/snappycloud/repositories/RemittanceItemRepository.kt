package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.RemittanceItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RemittanceItemRepository : JpaRepository<RemittanceItem, Long>