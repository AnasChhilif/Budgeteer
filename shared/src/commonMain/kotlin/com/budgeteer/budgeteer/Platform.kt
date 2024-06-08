package com.budgeteer.budgeteer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform