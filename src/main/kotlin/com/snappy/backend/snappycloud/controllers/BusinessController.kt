package com.snappy.backend.snappycloud.controllers

import com.snappy.backend.snappycloud.models.Business
import com.snappy.backend.snappycloud.models.Profile
import com.snappy.backend.snappycloud.services.BusinessService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api")
class BusinessController(
    private val businessService: BusinessService
){


    @PostMapping("/business/save")
    fun saveBusiness(@RequestBody business: Business): ResponseEntity<Business> =
        ResponseEntity.ok().body(this.businessService.save(business))


}