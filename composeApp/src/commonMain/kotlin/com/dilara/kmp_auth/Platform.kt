package com.dilara.kmp_auth

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform