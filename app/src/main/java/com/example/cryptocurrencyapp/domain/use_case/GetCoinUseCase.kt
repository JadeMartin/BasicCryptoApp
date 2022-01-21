package com.example.cryptocurrencyapp.domain.use_case

import com.example.cryptocurrencyapp.common.Resource
import com.example.cryptocurrencyapp.data.remote.dto.toCoinDetail
import com.example.cryptocurrencyapp.domain.model.CoinDetail
import com.example.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    // Operator fun can call the use case like a function
    // Flow -> Emit multiple values over time eg loading -> successful || error
     operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
         try {
             emit(Resource.Loading<CoinDetail>())
             val coin = repository.getCoinsById(coinId).toCoinDetail()
             emit(Resource.Success<CoinDetail>(coin))
         } catch(e: HttpException) {
             emit(Resource.Error<CoinDetail>(e.localizedMessage ?: "An unexpected error occurred."))
         } catch(e: IOException) {
             emit(Resource.Error<CoinDetail>("Couldn't reach server. Please check your internet connection"))
         }
     }
}