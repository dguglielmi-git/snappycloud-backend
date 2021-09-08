package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User,Long> {
    fun findByUsernameAndActive(username: String,active: Int): User?
}