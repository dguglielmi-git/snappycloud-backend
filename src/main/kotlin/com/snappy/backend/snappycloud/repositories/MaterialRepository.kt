package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Material
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MaterialRepository : JpaRepository<Material, Long>