package com.snappy.backend.snappycloud.models

import java.io.Serializable
import javax.persistence.Embeddable

@Embeddable
class UserBusinessRoleId(
        val businessId: Long,
        val profileId: Long,
        val userId: Long,
) : Serializable {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }

}