package com.example.cryptocurrencyapp.domain.use_case

import com.example.cryptocurrencyapp.common.Resource
import com.example.cryptocurrencyapp.common.ResourceHandler
import com.example.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.example.cryptocurrencyapp.data.repository.CoinRepositoryImpl
import com.example.cryptocurrencyapp.domain.model.CoinPrice
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetPriceHistoryTest {
    private lateinit var getPriceHistory: GetPriceHistory
    private lateinit var server: ResourceHandler

    /// Test Data
    private var coinFile: String = "json/priceHistory.json"
    private var coinId: String = "btc-bitcoin"
    private var pricePeriod: Long = 7
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
        getPriceHistory = GetPriceHistory(CoinRepositoryImpl(api))
    }

    @After
    fun teardown() {
        server.shutdown()

    }

    @Test
    fun `Get coin price history omits loading resource first`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_OK)
        val priceHistory = getPriceHistory(coinId, pricePeriod).first()

        assert(priceHistory is Resource.Loading<List<CoinPrice>>)
    }

    @Test
    fun `Get coin price history successful request`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_OK)
        val coin = getPriceHistory(coinId, pricePeriod).last()

        assert(coin is Resource.Success<List<CoinPrice>>)
        assert(coin.data!!.size == pricePeriod.toInt())
    }

    @Test
    fun `Get coin price history failed request`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_NOT_FOUND)
        val coin = getPriceHistory(coinId, pricePeriod).last()

        assert(coin is Resource.Error<List<CoinPrice>>)
        assert(coin.message == response404)
    }
}