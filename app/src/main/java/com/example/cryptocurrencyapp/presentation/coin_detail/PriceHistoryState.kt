package com.example.cryptocurrencyapp.presentation.coin_detail

import com.example.cryptocurrencyapp.common.Constants
import com.example.cryptocurrencyapp.domain.model.CoinHistory

data class PriceHistoryState(
    val isLoading: Boolean = false,
    val history: CoinHistory? = null,
    val pricePeriod: Long = Constants.DAYS_IN_A_WEEK,
    val error: String = ""
)