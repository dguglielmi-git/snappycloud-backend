package com.snappy.backend.snappycloud

import com.snappy.backend.snappycloud.models.Profile
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.services.ProfileService
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
            null,
            null,
            null
        ))
*/
       // profileService.addProfileToUser("dguglielmi","ROLE_ROOT")


    }
}