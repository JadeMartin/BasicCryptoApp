package com.example.cryptocurrencyapp.domain.model

data class CoinPrice(
    val date: String,
    val price: Double,
    val marketCap: Long
)
