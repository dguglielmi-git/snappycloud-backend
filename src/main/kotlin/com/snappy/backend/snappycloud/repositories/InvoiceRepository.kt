package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Invoice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<Invoice, Long>