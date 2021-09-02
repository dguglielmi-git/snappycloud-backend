package com.snappy.backend.snappycloud.services

interface IGenericService<T, ID> {
    fun findAll(): List<Any>
    fun findById(id: ID): Any?
    fun save(t: T): T
    fun update(t: T): T
    fun deleteById(id: ID): T
}