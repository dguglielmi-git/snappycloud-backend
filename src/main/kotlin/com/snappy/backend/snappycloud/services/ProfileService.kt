package com.snappy.backend.snappycloud.services

import com.snappy.backend.snappycloud.models.Profile
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.repositories.ProfileRepository
import com.snappy.backend.snappycloud.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository
) : GenericService<Profile,Long> {

    fun addProfileToUser(username: String, profileName: String) {
        val user: User? = userRepository.findByUsername(username)
        val profile: Profile? = profileRepository.findByName(profileName)
        user?.profiles?.add(profile)
    }

    override fun findAll(): List<Profile> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Profile? {
        TODO("Not yet implemented")
    }

    override fun save(t: Profile): Profile {
        TODO("Not yet implemented")
    }

    override fun update(t: Profile): Profile {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long): Profile {
        TODO("Not yet implemented")
    }
}