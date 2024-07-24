package com.codeancy.translator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform