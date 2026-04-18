package org.newsreader.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform