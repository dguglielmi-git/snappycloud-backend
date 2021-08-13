package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long>