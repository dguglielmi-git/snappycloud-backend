package com.snappy.backend.snappycloud

import com.snappy.backend.snappycloud.auth.UserLogged
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.utils.Common
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties

@Component
class RoleTest {

    @Test
    fun `Role Test - Adding Role`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("dguglielmi")

        usersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        usersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)
        usersLogged.addProfile("dguglielmi", "ROLE_MANAGER", 1)

        val result = usersLogged.getProfiles("dguglielmi", 1)

        val expected: Array<String> = arrayOf("ROLE_MANAGER", "ROLE_ADMIN", "ROLE_ROOT").sortedArray()
        Assertions.assertArrayEquals(expected, result)
    }

    @Test
    fun `Role Test - User has a profile`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("dguglielmi")
        usersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        usersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)

        val profileToCheck = arrayOf("ROLE_ROOT")

        val result = usersLogged.hasProfile("dguglielmi", profileToCheck, 1)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun `Role Test - Getting profiles from existing business`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.resetProfiles(1)
        usersLogged.removeUser("testing")
        usersLogged.addUser("testing")
        usersLogged.addProfile("testing", "ROLE_ROOT", 1)
        usersLogged.addProfile("testing", "ROLE_ADMIN", 1)

        val profileToCheck = arrayOf("ROLE_ROOT", "ROLE_ADMIN").sortedArray()

        val result = usersLogged.getProfiles("testing", 1)
        println(result?.toList().toString())

        Assertions.assertArrayEquals(profileToCheck, result)
    }

    @Test
    fun `Role Test - User has not a profile`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("test")
        usersLogged.addProfile("test", "ROLE_ROOT", 1)
        usersLogged.addProfile("test", "ROLE_ADMIN", 1)

        val profileToCheck = arrayOf("ROLE_COOL")

        val result = usersLogged.hasProfile("test", profileToCheck, 1)

        Assertions.assertEquals(false, result)
    }


    @Test
    fun `Role Test - Getting EMPTY profiles from non existing business`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("dguglielmi")
        usersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        usersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)

        val result = usersLogged.getProfiles("dguglielmi", 2)

        Assertions.assertArrayEquals(arrayOf(""), result)
    }

    @Test
    fun `Role Test - Verifying wrong profiles`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("dguglielmi")
        usersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        usersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)
        usersLogged.addProfile("dguglielmi", "ROLE_MANAGER", 1)

        val profiles = arrayOf("ROLE_ROOT", "ROLE_ADMIN", "ROLE_USER")

        val result = usersLogged.hasProfile("dguglielmi", profiles, 2)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `Role Test - Verifying right profiles`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("dguglielmi")
        usersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        usersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)
        usersLogged.addProfile("dguglielmi", "ROLE_MANAGER", 1)

        val profiles = arrayOf("ROLE_ROOT", "ROLE_ADMIN", "ROLE_MANAGER")

        val result = usersLogged.hasProfile("dguglielmi", profiles, 1)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun `Role Test - Remove an user`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("testing")
        usersLogged.addUser("john")
        usersLogged.removeUser("testing")

        val result = usersLogged.isUser("testing")

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `Role Test - Remove a profile`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addProfile("test", "TEST_ROLE", 99)
        val result = usersLogged.getProfiles("test", 99)
        Assertions.assertArrayEquals(arrayOf("TEST_ROLE"), result)

        usersLogged.removeProfile("test", "TEST_ROLE", 99)
        val result2 = usersLogged.getProfiles("test", 99)
        println(result2.toString())
        Assertions.assertArrayEquals(arrayOf(), result2)
    }

    @Test
    fun `Role Test - Check if User Exists`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("testing")
        usersLogged.addUser("john")

        val result = usersLogged.isUser("testing")
        Assertions.assertEquals(true, result)
    }

    @Test
    fun `Role Test - Check if User Does not exist`() {
        val usersLogged = UserLogged.getSession()
        usersLogged.addUser("testing")
        usersLogged.addUser("john")

        val result = usersLogged.isUser("jack")
        Assertions.assertEquals(false, result)
    }

}