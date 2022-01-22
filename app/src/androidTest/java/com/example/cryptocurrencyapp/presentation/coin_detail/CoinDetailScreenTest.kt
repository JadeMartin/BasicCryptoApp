package com.example.cryptocurrencyapp.presentation.coin_detail

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cryptocurrencyapp.di.AppModule
import com.example.cryptocurrencyapp.presentation.Screen
import com.example.cryptocurrencyapp.presentation.ui.MainActivity
import com.example.cryptocurrencyapp.presentation.ui.theme.CryptocurrencyAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(AppModule::class)
class CoinDetailScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            CryptocurrencyAppTheme {
                NavHost(navController = navController, startDestination = Screen.CoinDetailScreen.route) {
                    composable(route = Screen.CoinDetailScreen.route + "/{coinId}") {
                       CoinDetailScreen()
                    }
                }

            }
        }
    }

    // Find header text


}