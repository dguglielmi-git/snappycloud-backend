package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "document_type")
data class DocumentType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val description: String
)