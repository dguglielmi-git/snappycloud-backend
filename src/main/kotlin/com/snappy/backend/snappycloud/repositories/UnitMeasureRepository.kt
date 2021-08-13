package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.UnitMeasure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UnitMeasureRepository : JpaRepository<UnitMeasure, Long>