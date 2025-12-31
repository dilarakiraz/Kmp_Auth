package com.dilara.kmp_auth.domain.entity

data class User(
    val id: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?
)


