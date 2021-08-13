package com.snappy.backend.snappycloud.controllers

import com.snappy.backend.snappycloud.dtos.ProfileToUserDTO
import com.snappy.backend.snappycloud.models.Profile
import com.snappy.backend.snappycloud.services.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api")
class ProfileController(
    private val profileService: ProfileService,
) {

    @PostMapping("/profile/save")
    fun saveProfile(@RequestBody profileName: Profile): ResponseEntity<Profile>{
        val uri: URI =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/profile/save").toUriString())
        return ResponseEntity.created(uri).body(profileService.save(profileName))
    }

    @PostMapping("/profile/addtouser")
    fun addProfileToUser(@RequestBody form: ProfileToUserDTO): ResponseEntity<Any> {
        profileService.addProfileToUser(form.username, form.profileName)
        return ResponseEntity.ok().build()
    }
}