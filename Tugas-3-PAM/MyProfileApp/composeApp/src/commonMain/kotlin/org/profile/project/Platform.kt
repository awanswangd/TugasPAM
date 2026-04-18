package org.profile.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform