package com.snappy.backend.snappycloud.services

import com.snappy.backend.snappycloud.dtos.UserDTO
import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.repositories.UserRepository
import javassist.bytecode.ExceptionTable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
@Transactional
class UserService(
        private val userRepository: UserRepository,
) : UserDetailsService, IGenericService<User, Long> {
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    override fun save(user: User): UserDTO {
        user.password = user.password?.let { encode(it) }
        user.issueDate = GregorianCalendar(
                Calendar.YEAR,
                Calendar.MONTH,
                Calendar.DAY_OF_MONTH)
        user.active = 1
        return parseUserToDTO(this.userRepository.save(user))
    }

    fun getById(id: Long): UserDTO? = parseUserToDTO(this.userRepository.getById(id))

    fun findByUsername(username: String): User? =
            this.userRepository.findByUsernameAndActive(username, 1)

    override fun findAll(): List<UserDTO> {
        val users: MutableList<UserDTO> = mutableListOf()
        userRepository.findAll().forEach { user ->
            users.add(parseUserToDTO(user))
        }
        return users.toList()
    }

    override fun findById(id: Long): User? = this.userRepository.findByIdOrNull(id)

    override fun update(t: User): UserDTO {
        try {
            this.findById(t.id).also {
                t.password = it?.password
            }.apply {
                return parseUserToDTO(this@UserService.userRepository.save(t))
            }
        } catch (ex: Exception) {
            throw Exception("${t.id} does not exist")
        }
    }

    override fun deleteById(id: Long): User {
        return this.findById(id)?.apply {
            this@UserService.userRepository.deleteById(id)
        } ?: throw EntityNotFoundException("$id does not exist")
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        userRepository.findByUsernameAndActive(username, 1)?.let {
            val authorities = mutableListOf<SimpleGrantedAuthority>()
            authorities.add(SimpleGrantedAuthority("ROLE"))
            return org.springframework.security.core.userdetails.User(
                    it.username,
                    it.password,
                    authorities)
        }
        throw UsernameNotFoundException("User not found in the database")
    }

    fun updateUserPassword(user: String, pass: String) {
        try {
            this.userRepository.findByUsernameAndActive(user, 1)?.let {
                it.password = encode(pass)
                this@UserService.userRepository.save(it)
            }
        } catch (ex: Exception) {
            throw Exception("User not found")
        }
    }

    fun encode(str: String) = passwordEncoder.encode(str)

    private fun parseUserToDTO(user: User): UserDTO =
            UserDTO(user.id,
                    user.employeeCode,
                    user.username,
                    user.name,
                    user.surname,
                    user.email,
                    user.active,
                    user.issueDateString,
                    user.dniSsnNino)
}
