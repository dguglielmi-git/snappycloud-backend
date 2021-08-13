package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.SaleTax
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SaleTaxRepository : JpaRepository<SaleTax, Long>