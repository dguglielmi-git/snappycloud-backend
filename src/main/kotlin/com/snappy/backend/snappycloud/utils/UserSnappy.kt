package com.snappy.backend.snappycloud.utils

import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.UUID

@Component
open class UserSnappy{

    private val businessProfiles: MutableMap<Long, Array<String>> =
            mutableMapOf()

    fun addProfileToBusiness(businessId: Long, profile: String) {
        val profList = this.businessProfiles.get(businessId)
        if (profList == null) {
            this.businessProfiles.put(businessId, arrayOf(profile))
        } else {
            val temp: MutableList<String> = profList.toMutableList()
            temp.add(profile)
            this.businessProfiles.put(businessId, temp.toTypedArray())
        }
    }

    fun getProfiles(businessId: Long): Array<String>? = this.businessProfiles.get(businessId)



}