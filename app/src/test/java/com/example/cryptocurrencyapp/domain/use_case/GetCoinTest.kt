package com.example.cryptocurrencyapp.domain.use_case

import com.example.cryptocurrencyapp.common.Resource
import com.example.cryptocurrencyapp.common.ResourceHandler
import com.example.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.example.cryptocurrencyapp.data.repository.CoinRepositoryImpl
import com.example.cryptocurrencyapp.domain.model.CoinDetail
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetCoinTest {
    private lateinit var getCoin: GetCoinUseCase
    private lateinit var server: ResourceHandler

    /// Test Data
    private var coinFile: String = "json/coin.json"
    private var coinId: String = "btc-bitcoin"
    private var response404: String = "HTTP 404 Client Error"

    @Before
    fun setUp() {
        val webServer = MockWebServer()
        webServer.start()

        val api = Retrofit.Builder()
            .baseUrl(webServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)

        server = ResourceHandler(webServer)
        getCoin = GetCoinUseCase(CoinRepositoryImpl(api))
    }

    @After
    fun teardown() {
        server.shutdown()

    }

    @Test
    fun `Get coin by id omits loading resource first`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_OK)
        val coin = getCoin(coinId).first()

        assert(coin is Resource.Loading<CoinDetail>)
    }

    @Test
    fun `Get coin by id successful request`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_OK)
        val coin = getCoin(coinId).last()

        assert(coin is Resource.Success<CoinDetail>)
        assert(coin.data!!.coinId == coinId)
    }

    @Test
    fun `Get coin by id failed request`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_NOT_FOUND)
        val coin = getCoin(coinId).last()

        assert(coin is Resource.Error<CoinDetail>)
        assert(coin.message == response404)
    }
}