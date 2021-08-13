package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.BranchOffice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BranchOfficeRepository: JpaRepository<BranchOffice,Long>