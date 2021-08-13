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
    val id: Long = 0,
    @Column(name = "employee_code")
    var employeeCode: String? = null,
    @get:Size(max = 50)
    @Column(name = "username", unique = true)
    val username: String,
    @JsonIgnore
    var password: String,
    var name: String,
    var surname: String,
    var email: String,
    var active: Int,
    @Column(name = "issue_date")
    val issueDate: Date,
    @Column(name = "dni_ssn_nino")
    var dniSsnNino: String? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    var profiles: MutableList<Profile?> = mutableListOf(),
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_owner_business_fk")
    var business: List<Business> = mutableListOf<Business>()
)