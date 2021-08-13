package com.snappy.backend.snappycloud.services

import com.snappy.backend.snappycloud.models.User
import com.snappy.backend.snappycloud.repositories.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserService(private val userRepository: UserRepository) : GenericService<User, Long> {

    override fun save(user: User) = this.userRepository.save(user)

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
}
