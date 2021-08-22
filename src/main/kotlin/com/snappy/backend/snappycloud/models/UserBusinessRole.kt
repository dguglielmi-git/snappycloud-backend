package com.snappy.backend.snappycloud.models

import javax.persistence.*

@Entity
@Table(name = "user_business_role")
class UserBusinessRole(
        @EmbeddedId
        private val businessUserRoleId: UserBusinessRoleId,
        @ManyToOne
        @MapsId("businessId")
        val business: Business,
        @ManyToOne
        @MapsId("profileId")
        val profile: Profile,
        @ManyToOne
        @MapsId("userId")
        val user: User
)