package com.example.cryptocurrencyapp.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.common.Constants
import com.example.cryptocurrencyapp.common.Resource
import com.example.cryptocurrencyapp.common.getDate
import com.example.cryptocurrencyapp.domain.use_case.GetCoin
import com.example.cryptocurrencyapp.domain.use_case.GetPriceHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase : GetCoin,
    private val getPriceHistoryUseCase : GetPriceHistory,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _coinState = mutableStateOf(CoinDetailState())
    val coinState: State<CoinDetailState> = _coinState

    private val _historyState = mutableStateOf(PriceHistoryState())
    val historyState: State<PriceHistoryState> = _historyState

    private var currentCoinId: String? = null

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            currentCoinId = coinId
            getCoin()
            getPriceHistory(Constants.TIME_WEEK)
        }
    }

    private fun getCoin() {
        currentCoinId?.let {
            getCoinUseCase(it).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _coinState.value = CoinDetailState(
                            coin = result.data
                        )
                    }
                    is Resource.Error -> {
                        _coinState.value = CoinDetailState(
                            error = result.message ?: Constants.DEFAULT_ERROR
                        )
                    }
                    is Resource.Loading -> {
                        _coinState.value = CoinDetailState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getPriceHistory(dateWindow: Long) {
        currentCoinId?.let {
            getPriceHistoryUseCase(it, getDate(dateWindow)).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _historyState.value = PriceHistoryState(
                            history = result.data ?: emptyList(),
                            dateWindow = dateWindow
                        )
                    }
                    is Resource.Error -> {
                        _historyState.value = PriceHistoryState(
                            error = result.message ?: Constants.DEFAULT_ERROR,
                            dateWindow = dateWindow
                        )
                    }
                    is Resource.Loading -> {
                        _historyState.value = PriceHistoryState(
                            isLoading = true,
                            dateWindow = dateWindow
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: CoinEvent) {
        when(event) {
            is CoinEvent.DateChange -> {
                getPriceHistory(event.dateWindow)
            }
        }

    }
}