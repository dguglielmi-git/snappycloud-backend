package com.snappy.backend.snappycloud.controllers

import com.snappy.backend.snappycloud.services.ProfileService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ProfileController(
    private val profileService: ProfileService,
) {

//    @PostMapping("/profile/save")
//    fun saveProfile(@RequestBody profile: Profile): ResponseEntity<Profile> {
//        val uri: URI =
//            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/profile/save").toUriString())
//        return ResponseEntity.created(uri).body(profileService.save(profile))
//    }

//    @PostMapping("/profile/addAdminUser")
//    fun addAdminUser(@RequestBody form: ProfileToUserDTO) =
//        addProfile(form.username, SnappyConst.SNP_ADMIN, form.businessId)
//
//    @PostMapping("/profile/addManagerUser")
//    fun addManagerUser(@RequestBody form: ProfileToUserDTO) =
//        addProfile(form.username, SnappyConst.SNP_MANAGER, form.businessId)
//
//    @PostMapping("/profile/addUser")
//    fun addUser(@RequestBody form: ProfileToUserDTO) =
//        addProfile(form.username, SnappyConst.SNP_USER, form.businessId)
//
}