package com.snappy.backend.snappycloud.auth.login

import org.springframework.stereotype.Component

@Component
class UserSnappyInMemory : IGenericUserLogged {

    private val businessProfiles: MutableMap<Long, Set<String>> =
            mutableMapOf()

    override fun addProfileToBusiness(businessId: Long, profile: String) {
        val profList = this.businessProfiles.get(businessId)
        if (profList == null) {
            this.businessProfiles.put(businessId, setOf(profile))
        } else {
            val temp: MutableList<String> = profList.toMutableList()
            temp.add(profile)
            this.businessProfiles.put(businessId, temp.toSet())
        }
    }

    override fun removeProfileOfBusiness(businessId: Long, profile: String) {
        val profList = this.businessProfiles.get(businessId)
        val newList = profList?.filter { it != profile }
        if (newList != null) {
            this.businessProfiles.put(businessId, newList.toSet())
        } else {
            this.businessProfiles.remove(businessId)
        }
    }

    override fun getProfiles(businessId: Long): Array<String>? = this.businessProfiles.get(businessId)?.toTypedArray()

    override fun resetProfiles(businessId: Long) {
        this.businessProfiles.remove(businessId)
    }

}