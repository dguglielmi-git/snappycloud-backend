package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentMethodRepository : JpaRepository<PaymentMethod, Long>