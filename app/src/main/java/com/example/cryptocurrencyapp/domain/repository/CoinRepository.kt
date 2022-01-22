package com.example.cryptocurrencyapp.domain.repository

import com.example.cryptocurrencyapp.data.remote.dto.CoinDetailDto
import com.example.cryptocurrencyapp.data.remote.dto.CoinDto
import com.example.cryptocurrencyapp.data.remote.dto.CoinPriceDto

interface CoinRepository {

    suspend fun getCoins() : List<CoinDto>
    suspend fun getCoinsById(coinId: String) : CoinDetailDto
    suspend fun getPriceHistory(coinId: String, start: String) : List<CoinPriceDto>
}