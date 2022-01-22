package com.example.cryptocurrencyapp.common

import java.time.LocalDate

fun getDate(days : Long) : String {
    return LocalDate.now().minusDays(days).toString()
}