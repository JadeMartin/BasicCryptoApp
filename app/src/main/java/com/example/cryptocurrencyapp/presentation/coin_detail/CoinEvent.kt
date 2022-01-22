package com.example.cryptocurrencyapp.presentation.coin_detail

sealed class CoinEvent {
    data class DateChange(val pricePeriod: Long): CoinEvent()
}
