package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Business
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BusinessRepository : JpaRepository<Business, Long>