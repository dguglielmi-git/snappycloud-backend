package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByNameAndId(profileName: String, id: Long): Profile?
}
