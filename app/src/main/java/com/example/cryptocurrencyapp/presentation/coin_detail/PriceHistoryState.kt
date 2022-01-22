package com.example.cryptocurrencyapp.presentation.coin_detail

import com.example.cryptocurrencyapp.common.Constants
import com.example.cryptocurrencyapp.domain.model.CoinPrice

data class PriceHistoryState(
    val isLoading: Boolean = false,
    val history: List<CoinPrice> = emptyList(),
    val dateWindow: Long = Constants.TIME_WEEK,
    val error: String = ""
)