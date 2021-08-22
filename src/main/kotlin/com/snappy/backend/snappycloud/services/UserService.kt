package com.snappy.backend.snappycloud.services

import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.repositories.UserRepository
import com.snappy.backend.snappycloud.utils.UsersLogged
import javassist.Loader
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
@Transactional
class UserService(
        private val userRepository: UserRepository,
        private val businessService: BusinessService,
) : UserDetailsService, GenericService<User, Long> {
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    override fun save(user: User): User {
        user.password = passwordEncoder.encode(user.password)
        val year = Calendar.YEAR
        val month = Calendar.MONTH
        val day = Calendar.DAY_OF_MONTH
        user.issueDate = GregorianCalendar(year, month, day)
        user.active = 1
        return this.userRepository.save(user)
    }

    fun getById(id: Long): User? = this.userRepository.getById(id)

    fun findByUsername(username: String): User? =
            this.userRepository.findByUsername(username)

    override fun findAll(): List<User> = this.userRepository.findAll()

    override fun findById(id: Long): User? = this.userRepository.findByIdOrNull(id)

    override fun update(t: User): User {
        return if (this.userRepository.existsById(t.id))
            this.userRepository.save(t)
        else throw EntityNotFoundException("${t.id} does not exist")
    }

    override fun deleteById(id: Long): User {
        return this.findById(id)?.apply {
            this@UserService.userRepository.deleteById(id)
        } ?: throw EntityNotFoundException("$id does not exist")
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername("dguglielmi")
        if (user != null) {
            val authorities = mutableListOf<SimpleGrantedAuthority>()
            authorities.add(SimpleGrantedAuthority("ROLE"))
            return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
        } else throw UsernameNotFoundException("User not found in the database")
    }
}
