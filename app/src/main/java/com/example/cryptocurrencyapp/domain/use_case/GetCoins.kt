package com.example.cryptocurrencyapp.domain.use_case

import com.example.cryptocurrencyapp.common.Constants
import com.example.cryptocurrencyapp.common.Resource
import com.example.cryptocurrencyapp.data.remote.dto.toCoin
import com.example.cryptocurrencyapp.domain.model.Coin
import com.example.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoins @Inject constructor(
    private val repository: CoinRepository
) {
    // Operator fun can call the use case like a function
    // Flow -> Emit multiple values over time eg loading -> successful || error
     operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
         try {
             emit(Resource.Loading<List<Coin>>())
             val coins = repository.getCoins().map { it.toCoin() }
             emit(Resource.Success<List<Coin>>(coins))
         } catch(e: HttpException) {
             emit(Resource.Error<List<Coin>>(e.localizedMessage ?: Constants.DEFAULT_ERROR))
         } catch(e: IOException) {
             emit(Resource.Error<List<Coin>>(Constants.DEFAULT_ERROR_INTERNET))
         }
    }
}