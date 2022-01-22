package com.example.cryptocurrencyapp.data.remote.dto

import com.example.cryptocurrencyapp.domain.model.CoinPrice
import com.google.gson.annotations.SerializedName

data class CoinPriceDto(
    @SerializedName("close")
    val close: Double,
    @SerializedName("high")
    val high: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("market_cap")
    val marketCap: Long,
    @SerializedName("open")
    val `open`: Double,
    @SerializedName("time_close")
    val timeClose: String,
    @SerializedName("time_open")
    val timeOpen: String,
    @SerializedName("volume")
    val volume: Long
)

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        date = timeClose,
        price = close,
        marketCap = marketCap
    )
}