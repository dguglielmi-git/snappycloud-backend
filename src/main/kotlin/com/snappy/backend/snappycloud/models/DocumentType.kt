package com.snappy.backend.snappycloud.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class DocumentType(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val description: String
)