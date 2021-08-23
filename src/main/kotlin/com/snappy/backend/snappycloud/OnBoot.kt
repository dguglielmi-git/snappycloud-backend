package com.snappy.backend.snappycloud

import com.snappy.backend.snappycloud.models.*
import com.snappy.backend.snappycloud.services.BusinessService
import com.snappy.backend.snappycloud.services.ProfileService
import com.snappy.backend.snappycloud.services.UserBusinessRoleService
import com.snappy.backend.snappycloud.services.UserService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class OnBoot(
        private val userService: UserService,
        private val profileService: ProfileService,
        private val businessService: BusinessService,
        private val userBusinessRoleService: UserBusinessRoleService,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
/*
        profileService.save(Profile(0, "ROLE_ROOT"))
        profileService.save(Profile(0, "ROLE_ADMIN"))
        profileService.save(Profile(0, "ROLE_MANAGER"))
        profileService.save(Profile(0, "ROLE_USER"))

        userService.save(User(0,
            null,
            "dguglielmi",
            "password",
            "Daniel",
            "Guglielmi",
            "dag@danielguglielmi.dev",
            1, GregorianCalendar(2021,8,14),
            null
        ))
*/
/*
        val user1 = userService.findByUsername("dguglielmi")
        val business = businessService.save(
                Business(
                        0,
                        "Testing 2",
                        "Testing Business 2",
                        1,
                        GregorianCalendar(2021, 8, 22),
                        user1!!,
                        null
                ))
*/
/*
        val user = userService.findByUsername("dguglielmi")
        val profile = profileService.findById(1)
        val business = businessService.findById(6)

        val upb = UserBusinessRoleId(user!!.id,profile!!.id,business!!.id)
        val result = userBusinessRoleService.save(UserBusinessRole(upb,business,profile,user))
        println(result)
*/
/*
        val user = userService.findByUsername("dguglielmi")
        val business = businessService.findById(6)
        val result : Array<UserBusinessRole>?
        = userBusinessRoleService.findByUserAndBusiness(user!!.id,6)

        result?.forEach {
            res -> println("${res.user.username} - ${res.profile.name}")
        }
        */
/*
        val user = userService.findByUsername("dguglielmi")
        val result: Array<UserBusinessRole>? = userBusinessRoleService.findByUser(user!!.id)

        result?.forEach { res ->
            println("${res.business.id}- ${res.profile.name}")
        }
*/
    }
}