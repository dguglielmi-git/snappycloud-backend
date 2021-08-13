package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long>