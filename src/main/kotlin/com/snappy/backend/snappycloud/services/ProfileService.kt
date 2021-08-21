package com.snappy.backend.snappycloud.services

import com.snappy.backend.snappycloud.constants.SnappyConst
import com.snappy.backend.snappycloud.models.Business
import com.snappy.backend.snappycloud.models.Profile
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.repositories.BusinessRepository
import com.snappy.backend.snappycloud.repositories.ProfileRepository
import com.snappy.backend.snappycloud.repositories.UserRepository
import com.snappy.backend.snappycloud.utils.UsersLogged
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
@Transactional
class ProfileService(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val businessRepository: BusinessRepository,
) : GenericService<Profile, Long> {
    override fun findAll(): List<Profile> = this.profileRepository.findAll()

    override fun findById(id: Long): Profile? = this.profileRepository.findByIdOrNull(id)

    override fun save(profile: Profile): Profile = this.profileRepository.save(profile)

    override fun update(profile: Profile): Profile {
        return if (this.profileRepository.existsById(profile.id))
            this.profileRepository.save(profile)
        else throw EntityNotFoundException("${profile.id} does not exist")
    }

    override fun deleteById(id: Long): Profile {
        return this.findById(id).apply {
            this@ProfileService.profileRepository.deleteById(id)
        } ?: throw EntityNotFoundException("$id does not exist")
    }

    fun addProfileToUser(username: String, profileName: String, businessId: Long) {
        val user: User? = userRepository.findByUsername(username)
        val profile: Profile? = this.profileRepository.findByNameAndId(profileName, businessId)

        // Verify if the user is the business owner
        if (profileName == SnappyConst.SNP_ROOT) {
            val business: Business? = this.businessRepository.findByIdOrNull(businessId)
            if (business != null && profile != null) {
                if (business.owner == user) {
                    user.profiles?.add(profile)
                }
            }
        } else {
            if (profile != null) user?.profiles?.add(profile)
        }
    }

}