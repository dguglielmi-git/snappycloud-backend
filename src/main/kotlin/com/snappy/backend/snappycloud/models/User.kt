package com.snappy.backend.snappycloud.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import java.util.Calendar.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        val id: Long = 0,
        @Column(name = "employee_code")
        var employeeCode: String? = null,
        @get:Size(max = 50)
        @Column(unique = true)
        val username: String,
        @JsonIgnore
        var password: String,
        var name: String,
        var surname: String,
        var email: String,
        var active: Int,
        @Column(name = "issue_date")
        var issueDate: Calendar,
        @Column(name = "dni_ssn_nino")
        var dniSsnNino: String? = null
) {
    val issueDateString: String
        get() = getIssueDateFormatted(issueDate)

    private fun getIssueDateFormatted(issueDate: Calendar): String =
            "${issueDate.get(DAY_OF_MONTH)}/${issueDate.get(MONTH)}/${issueDate.get(YEAR)}"
}