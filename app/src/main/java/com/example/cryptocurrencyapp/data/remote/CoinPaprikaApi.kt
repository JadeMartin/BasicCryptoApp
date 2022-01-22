package com.example.cryptocurrencyapp.data.remote

import com.example.cryptocurrencyapp.common.Constants
import com.example.cryptocurrencyapp.data.remote.dto.CoinDetailDto
import com.example.cryptocurrencyapp.data.remote.dto.CoinDto
import com.example.cryptocurrencyapp.data.remote.dto.CoinPriceDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/v1/coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String) : CoinDetailDto

    @GET("/v1/coins/{coinId}/ohlcv/historical")
    suspend fun getPriceHistory(
        @Path("coinId") coinId: String,
        @Query("start") start : String,
        @Query("limit") limit : Int = Constants.MAX_HISTORY_LIMIT,
        ) : List<CoinPriceDto>
}