package com.snappy.backend.snappycloud

import com.snappy.backend.snappycloud.utils.UsersLogged
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.util.Assert

class RoleTest {

    @Test
    fun `Test - Adding Role`() {
        UsersLogged.addUser("dguglielmi")

        UsersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_MANAGER", 1)

        val result = UsersLogged.getProfiles("dguglielmi", 1)

        val expected: Array<String> = arrayOf("ROLE_MANAGER", "ROLE_ADMIN", "ROLE_ROOT").sortedArray()
        Assertions.assertArrayEquals(expected, result)
    }

    @Test
    fun `Test - User has a profile`() {
        UsersLogged.addUser("dguglielmi")
        UsersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)

        val profileToCheck = arrayOf("ROLE_ROOT")

        val result = UsersLogged.hasProfile("dguglielmi", profileToCheck,1)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun `Test - User has not a profile`() {
        UsersLogged.addUser("dguglielmi")
        UsersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)

        val profileToCheck = arrayOf("ROLE_COOL")

        val result = UsersLogged.hasProfile("dguglielmi", profileToCheck,1)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `Test - Getting profiles from existing business`() {
        UsersLogged.addUser("dguglielmi")
        UsersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)

        val profileToCheck = arrayOf("ROLE_ROOT","ROLE_ADMIN").sortedArray()

        val result = UsersLogged.getProfiles("dguglielmi",1)

        Assertions.assertArrayEquals(profileToCheck,result)
    }

    @Test
    fun `Test - Getting NULL profiles from non existing business`() {
        UsersLogged.addUser("dguglielmi")
        UsersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)

        val result = UsersLogged.getProfiles("dguglielmi",2)

        Assertions.assertArrayEquals(null,result)
    }

    @Test
    fun `Test - Verifying wrong profiles`() {

        UsersLogged.addUser("dguglielmi")
        UsersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_MANAGER", 1)

        val profiles = arrayOf("ROLE_ROOT", "ROLE_ADMIN", "ROLE_USER")

        val result = UsersLogged.hasProfile("dguglielmi", profiles, 2)

        Assertions.assertEquals(false, result)
    }

    @Test
    fun `Test - Verifying right profiles`() {

        UsersLogged.addUser("dguglielmi")
        UsersLogged.addProfile("dguglielmi", "ROLE_ROOT", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_ADMIN", 1)
        UsersLogged.addProfile("dguglielmi", "ROLE_MANAGER", 1)

        val profiles = arrayOf("ROLE_ROOT", "ROLE_ADMIN", "ROLE_MANAGER")

        val result = UsersLogged.hasProfile("dguglielmi", profiles, 1)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun `Test - Remove an user`() {
        UsersLogged.addUser("testing")
        UsersLogged.addUser("john")
        UsersLogged.removeUser("testing")

        val result = UsersLogged.isUser("testing")

        Assertions.assertEquals(false,result)
    }

    @Test
    fun `Test - Check if User Exists`() {
        UsersLogged.addUser("testing")
        UsersLogged.addUser("john")

        val result = UsersLogged.isUser("testing")

        Assertions.assertEquals(true,result)
    }

    @Test
    fun `Test - Check if User Does not exists`() {
        UsersLogged.addUser("testing")
        UsersLogged.addUser("john")

        val result = UsersLogged.isUser("jack")

        Assertions.assertEquals(false,result)
    }
}