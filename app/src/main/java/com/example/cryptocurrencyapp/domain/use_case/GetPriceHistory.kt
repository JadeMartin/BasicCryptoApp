package com.example.cryptocurrencyapp.domain.use_case

import com.example.cryptocurrencyapp.common.Constants
import com.example.cryptocurrencyapp.common.Resource
import com.example.cryptocurrencyapp.common.getDate
import com.example.cryptocurrencyapp.data.remote.dto.toCoinPrice
import com.example.cryptocurrencyapp.domain.model.CoinPrice
import com.example.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPriceHistory @Inject constructor(
    private val repository: CoinRepository
    ) {
        // Operator fun can call the use case like a function
        // Flow -> Emit multiple values over time eg loading -> successful || error
        operator fun invoke(coinId: String, pricePeriod: Long): Flow<Resource<List<CoinPrice>>> = flow {
            try {
                emit(Resource.Loading<List<CoinPrice>>())
                val startDate = getDate(pricePeriod)
                val priceHistory = repository.getPriceHistory(coinId, startDate, pricePeriod.toInt()).map { it.toCoinPrice() }
                emit(Resource.Success<List<CoinPrice>>(priceHistory))
            } catch(e: HttpException) {
                emit(Resource.Error<List<CoinPrice>>(e.localizedMessage ?: Constants.DEFAULT_ERROR))
            } catch(e: IOException) {
                emit(Resource.Error<List<CoinPrice>>(Constants.DEFAULT_ERROR_INTERNET))
            }
        }
}