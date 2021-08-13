package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long>