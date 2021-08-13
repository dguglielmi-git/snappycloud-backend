package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepository : JpaRepository<Currency, Long>