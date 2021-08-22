package com.snappy.backend.snappycloud.repositories

import com.snappy.backend.snappycloud.models.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserBusinessRoleRepository: JpaRepository<UserBusinessRole,UserBusinessRoleId> {
    fun findByBusinessId(businessId: Long): Array<UserBusinessRole>?
    fun findByProfileId(profileId: Long): Array<UserBusinessRole>?
    fun findByUserId(userId: Long): Array<UserBusinessRole>?
    fun findByUserIdAndBusinessId(userId: Long, businessId: Long): Array<UserBusinessRole>?
}