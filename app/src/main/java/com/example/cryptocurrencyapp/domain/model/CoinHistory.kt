package com.example.cryptocurrencyapp.domain.model

data class CoinHistory(
    val priceHistory: List<CoinPrice>,
    val priceMax: Double,
)
