package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.CurrencyRate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRateRepository : JpaRepository<CurrencyRate, Long>