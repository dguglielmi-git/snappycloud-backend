package com.snappy.backend.snappycloud.utils

object UsersLogged {
    private val users: MutableMap<String, UserSnappy> = mutableMapOf()

    private fun getUser(user: String): UserSnappy? = users.get(user)

    fun addUser(user: String) {
        val userBody = UserSnappy()
        this.users.put(user, userBody)
    }

    fun removeUser(user: String) = this.users.remove(user)

    fun hasProfile(username: String, profile: Array<String>, businessId: Long): Boolean {
        val user = getUser(username)
        var result = false
        if (user != null) {
            val userProfiles = user.getProfiles(businessId)
            if (userProfiles != null) {
                profile.forEach { prof ->
                    if (userProfiles.contains(prof)) result = true
                }
            }
        }
        return result
    }


    fun addProfile(username: String, profile: String, businessId: Long) {
        val user = getUser(username)
        if (user != null) {
            user.addProfileToBusiness(businessId, profile)
            users.put(username, user)
        }
    }

    fun getProfiles(username: String, businessId: Long): Array<String>? {
        val user = this.getUser(username)
        return if (user != null) user.getProfiles(businessId)?.sortedArray() else null
    }


}