package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Brand
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BrandRepository: JpaRepository<Brand,Long>