package com.snappy.backend.snappycloud.controllers
/*
import com.snappy.backend.snappycloud.dtos.Message
import com.snappy.backend.snappycloud.global.keys
import com.snappy.backend.snappycloud.services.GenericService
import com.snappy.backend.snappycloud.utils.SessionUtilities
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


abstract class GenericController<T, ID>(private val genericService: GenericService<T, ID>) {
    val sessionLogin = SessionUtilities()

    @ApiOperation("Get all entities")
    @GetMapping
    fun findAll() = genericService.findAll()

    @GetMapping("/{id}")
    fun findById(@CookieValue(keys.COOKIE_JWT_NAME) jwt: String?, @PathVariable id: ID): ResponseEntity<T> {
        val verifyUser = this.sessionLogin.verifyUser(jwt)
        if (verifyUser != 200) {
            ResponseEntity.status(verifyUser).body(Message("Unauthenticated"))
        }

        val entity = genericService.findById(id)
        return ResponseEntity.status(
            if (entity != null) HttpStatus.OK
            else HttpStatus.NO_CONTENT
        ).body(entity)
    }

    @PostMapping
    fun save(@RequestBody body: T) =
        ResponseEntity.status(HttpStatus.CREATED).body(this.genericService.save(body))

    @PutMapping
    fun update(@RequestBody body: T) = genericService.update(body)

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: ID) = genericService.deleteById(id)

}
*/
