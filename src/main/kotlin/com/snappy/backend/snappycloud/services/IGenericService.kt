package com.snappy.backend.snappycloud.services

interface IGenericService<T, ID> {
    fun findAll(): List<Any>
    fun findById(id: ID): Any?
    fun save(t: T): Any
    fun update(t: T): Any
    fun deleteById(id: ID): Any
}