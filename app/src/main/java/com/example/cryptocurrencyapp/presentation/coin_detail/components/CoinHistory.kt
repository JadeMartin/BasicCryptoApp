package com.example.cryptocurrencyapp.presentation.coin_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptocurrencyapp.presentation.coin_detail.PriceHistoryState

@Composable
fun CoinHistory(
    historyState: PriceHistoryState,
    modifier: Modifier = Modifier
) {

    if (historyState.history.isNotEmpty()) {
        LazyRow {
            items(historyState.history) { coinPrice ->
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = coinPrice.price.toString(),
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = coinPrice.date,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

    if (historyState.error.isNotBlank()) {
        Text(
            text = historyState.error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
    }
}