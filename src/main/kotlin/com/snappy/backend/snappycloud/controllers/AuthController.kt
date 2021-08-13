package com.snappy.backend.snappycloud.controllers
/*
import com.snappy.backend.snappycloud.dtos.LoginDTO
import com.snappy.backend.snappycloud.dtos.Message
import com.snappy.backend.snappycloud.dtos.RegisterDTO
import com.snappy.backend.snappycloud.global.keys
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.services.UserService
import com.snappy.backend.snappycloud.utils.SessionUtilities
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api")
class AuthController(
    private val userService: UserService
) {

    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<Any> {
        val user = User(
            username = body.username,
            name = body.name,
            surname = body.surname,
            email = body.email,
            active = 1,
            issueDate = Calendar.getInstance().time
        )
        user.password = body.password

        val checkUser = this.userService.findByUsername(body.username)
        if (checkUser != null) {
            return ResponseEntity.badRequest().body(Message("Username already taken."))
        }


        return ResponseEntity.ok(userService.save(user))

    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = this.userService.findByUsername(body.username)
            ?: return ResponseEntity.badRequest().body(Message("User or password invalid"))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(Message("User or password invalid"))
        }

        val issuer = user
        val sessionLogin = SessionUtilities(response = response)
        sessionLogin.loginUser(issuer)

        return ResponseEntity.ok(Message("login success."))
    }


    @GetMapping("user")
    fun user(@CookieValue(keys.COOKIE_JWT_NAME) jwt: String?): ResponseEntity<Any> {
        val sessionLogin = SessionUtilities()
        val verifyUser = sessionLogin.verifyUser(jwt)

        if (verifyUser != 200) {
            ResponseEntity.status(verifyUser).body(Message("Unauthenticated"))
        }

        val body = sessionLogin.getIssuer(jwt!!)
        return ResponseEntity.ok(this.userService.getById(body.issuer.toLong()))
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val sessionLogin = SessionUtilities(response = response)
        sessionLogin.logoffUser()
        return ResponseEntity.ok(Message("Successful logged out"))
    }
}

*/
