package com.snappy.backend.snappycloud.services

import com.snappy.backend.snappycloud.constants.SnappyConst
import com.snappy.backend.snappycloud.models.Business
import com.snappy.backend.snappycloud.models.Profile
import com.snappy.backend.snappycloud.repositories.BusinessRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
@Transactional
class BusinessService(
    private val businessRepository: BusinessRepository,
    private val profileService: ProfileService,
) : GenericService<Business, Long> {

    override fun findAll(): List<Business> = this.businessRepository.findAll()

    override fun findById(id: Long): Business? = this.businessRepository.findByIdOrNull(id)

    override fun save(t: Business): Business {
        var profile: MutableList<Profile> = mutableListOf<Profile>()

        profile.add(profileService.save(Profile(0, SnappyConst.SNP_ROOT)))
        profile.add(profileService.save(Profile(0, SnappyConst.SNP_ADMIN)))
        profile.add(profileService.save(Profile(0, SnappyConst.SNP_MANAGER)))
        profile.add(profileService.save(Profile(0, SnappyConst.SNP_USER)))
        t.profile = profile

        return this.businessRepository.save(t)
    }


    override fun update(t: Business): Business {
        return if (this.businessRepository.existsById(t.id))
            this.businessRepository.save(t)
        else throw EntityNotFoundException("${t.id} does not exist")
    }

    override fun deleteById(id: Long): Business {
        return this.findById(id).apply {
            this@BusinessService.businessRepository.deleteById(id)
        } ?: throw EntityNotFoundException("$id does not exist")
    }

    fun getProfiles(businessId: Long): List<Profile> {
        val business: Business? = this.businessRepository.findByIdOrNull(businessId)
        if (business != null) {
            return business.profile
        }
        throw Exception("No Profiles found for the business id:$businessId")
    }
}