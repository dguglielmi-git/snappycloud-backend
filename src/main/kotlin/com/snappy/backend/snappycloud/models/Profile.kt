package com.snappy.backend.snappycloud.models

import javax.persistence.*

/**
 * Profiles:
 *
 * ROLE_ROOT:       Business Owner, Full Access. This Role has total control of the business.
 * ROLE_ADMIN:      Administrator of several Branches, can add users to the Business.
 *                  This role has same privileges of ROOT, but not allowed to Remove Business and Branches.
 * ROLE_MANAGER:    Branch Office Manager, is allowed to add users to the Branch he/she is managing.
 * ROLE_USER:       Employee. Every user with this role can be assigned to many Business/Branches.
 */
@Entity
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,
    @Column(name = "profile_name")
    val name: String
)