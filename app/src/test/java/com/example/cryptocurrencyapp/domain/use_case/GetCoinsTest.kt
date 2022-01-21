package com.example.cryptocurrencyapp.domain.use_case

import com.example.cryptocurrencyapp.common.Resource
import com.example.cryptocurrencyapp.common.ResourceHandler
import com.example.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.example.cryptocurrencyapp.data.repository.CoinRepositoryImpl
import com.example.cryptocurrencyapp.domain.model.Coin
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

class GetCoinsTest {
    private lateinit var getCoins: GetCoins
    private lateinit var server: ResourceHandler

    /// Test Data
    private var coinFile: String = "json/coins.json"
    private var ids: List<String> =  listOf("btc-bitcoin", "eth-ethereum")
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
        getCoins = GetCoins(CoinRepositoryImpl(api))
    }

    @After
    fun teardown() {
        server.shutdown()

    }

    @Test
    fun `Get all coin omits loading resource first`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_OK)
        val coin = getCoins().first()

        assert(coin is Resource.Loading<List<Coin>>)
    }

    @Test
    fun `Get all coins successful request`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_OK)
        val coin = getCoins().last()

        assert(coin is Resource.Success<List<Coin>>)
        for (i in ids.indices) {
            assert(ids[i] == coin.data!![i].id)
        }
    }

    @Test
    fun `Get all coins failed request`() = runBlocking {
        server.uploadRequest(coinFile, HttpURLConnection.HTTP_NOT_FOUND)
        val coin = getCoins().last()

        assert(coin is Resource.Error<List<Coin>>)
        assert(coin.message == response404)
    }
}