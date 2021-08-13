package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Position
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PositionRepository : JpaRepository<Position, Long>