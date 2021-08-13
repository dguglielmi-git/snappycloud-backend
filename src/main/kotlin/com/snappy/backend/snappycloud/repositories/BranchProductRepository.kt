package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.BranchProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BranchProductRepository: JpaRepository<BranchProduct,Long>