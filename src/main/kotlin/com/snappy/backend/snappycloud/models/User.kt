package com.snappy.backend.snappycloud.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @get:Size(max = 50)
    val username: String,
    val name: String,
    val surname: String,
    val email: String,
    val active: Int,
    @Column(name = "issue_date")
    val issueDate: Date,
    @Column(name = "employee_code")
    val employeeCode: String?,
    @Column(name = "dni_ssn_nino")
    val dniSsnNino: String?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile")
    val profile: Profile,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    val business: Business
) {
    @Column(name = "password")
    var password = ""
        @JsonIgnore
        get() = field
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    fun comparePassword(password: String): Boolean {
        val passwordEncoder = BCryptPasswordEncoder()
        return BCryptPasswordEncoder().matches(password, this.password)
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other === this) return true
        if (this.javaClass != other.javaClass) return false
        other as User

        return this.id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}