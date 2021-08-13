package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.DocumentType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentTypeRepository : JpaRepository<DocumentType, Long>