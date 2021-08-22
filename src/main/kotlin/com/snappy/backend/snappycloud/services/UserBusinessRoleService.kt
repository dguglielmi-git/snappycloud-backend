package com.snappy.backend.snappycloud.services

import com.snappy.backend.snappycloud.models.UserBusinessRole
import com.snappy.backend.snappycloud.models.UserBusinessRoleId
import com.snappy.backend.snappycloud.repositories.UserBusinessRoleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
@Transactional
class UserBusinessRoleService(
        private val userBusinessRoleRepository: UserBusinessRoleRepository
) : GenericService<UserBusinessRole, UserBusinessRoleId> {
    override fun findAll(): List<UserBusinessRole> = this.userBusinessRoleRepository.findAll()

    override fun findById(id: UserBusinessRoleId): UserBusinessRole? =
            this.userBusinessRoleRepository.findByIdOrNull(id)

    override fun save(t: UserBusinessRole): UserBusinessRole = this.userBusinessRoleRepository.save(t)

    override fun update(t: UserBusinessRole): UserBusinessRole = this.userBusinessRoleRepository.save(t)

    override fun deleteById(id: UserBusinessRoleId): UserBusinessRole {
        return this.userBusinessRoleRepository.findByIdOrNull(id).apply {
            this@UserBusinessRoleService.userBusinessRoleRepository.deleteById(id)
        } ?: throw EntityNotFoundException("$id does not exist")
    }

    fun findByBusiness(businessId: Long) = this.userBusinessRoleRepository.findByBusinessId(businessId)

    fun findByProfile(profileId: Long) = this.userBusinessRoleRepository.findByProfileId(profileId)

    fun findByUser(userId: Long) = this.userBusinessRoleRepository.findByUserId(userId)

    fun findByUserAndBusiness(userId: Long, businessId: Long) =
            this.userBusinessRoleRepository.findByUserIdAndBusinessId(userId, businessId)
}