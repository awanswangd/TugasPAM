package org.notes.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform