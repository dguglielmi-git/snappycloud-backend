package com.snappy.backend.snappycloud.auth.login

import org.springframework.stereotype.Component

@Component
class UserSnappyInMemory : IGenericUserLogged {

    private val businessProfiles: MutableMap<Long, MutableSet<String>> =
            mutableMapOf()

    override fun addProfileToBusiness(businessId: Long, profile: String) {
        val profList = this.businessProfiles[businessId]
        if (profList == null) {
            this.businessProfiles[businessId] = mutableSetOf(profile)
        } else {
            val temp: MutableList<String> = profList.toMutableList()
            temp.add(profile)
            this.businessProfiles[businessId] = temp.toMutableSet()
        }
    }

    override fun removeProfileFromBusiness(businessId: Long, profile: String) {
        val profList = this.businessProfiles[businessId] ?: mutableSetOf()
        this.businessProfiles[businessId] = profList.filter { it != profile }.toMutableSet()
    }

    override fun getProfiles(businessId: Long): Array<String>? =
            this.businessProfiles[businessId]?.toTypedArray()

    override fun resetProfiles(businessId: Long) {
        this.businessProfiles.remove(businessId)
    }
}