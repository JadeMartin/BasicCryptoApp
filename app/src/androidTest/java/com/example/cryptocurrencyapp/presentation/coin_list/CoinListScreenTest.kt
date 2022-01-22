package com.example.cryptocurrencyapp.presentation.coin_list

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.cryptocurrencyapp.common.TestTags
import com.example.cryptocurrencyapp.di.AppModule
import com.example.cryptocurrencyapp.presentation.Screen
import com.example.cryptocurrencyapp.presentation.ui.MainActivity
import com.example.cryptocurrencyapp.presentation.ui.theme.CryptocurrencyAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.File
import java.io.InputStreamReader
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class CoinListScreenTest {
    private var coinFile: String = "json/coins.json"

    lateinit var webServer: MockWebServer

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        webServer = MockWebServer()
        webServer.start(8080)

        composeRule.setContent {
            val navController = rememberNavController()
            CryptocurrencyAppTheme {
                NavHost(navController = navController, startDestination = Screen.CoinListScreen.route) {
                    composable(route = Screen.CoinListScreen.route) {
                        CoinListScreen(navController = navController)
                    }
                }

            }
        }
    }

    @After
    fun tearDown() {
        webServer.shutdown()
    }

    // Find first coin
    // Click & navigate to coin

    @Test
    fun clickOnCoin_isClickable(): Unit = runBlocking {
        webServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getJsonFile(coinFile)))

        composeRule.wait()

        composeRule.onAllNodesWithTag(TestTags.COIN_ROW)[0]
            .assertHasClickAction()
    }

    private fun getJsonFile(path : String) : String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path)).use { it.readText() }
    }



}