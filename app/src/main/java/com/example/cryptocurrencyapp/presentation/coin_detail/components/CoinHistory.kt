package com.example.cryptocurrencyapp.presentation.coin_detail.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptocurrencyapp.domain.model.CoinPrice
import com.example.cryptocurrencyapp.presentation.coin_detail.PriceHistoryState

@Composable
fun CoinHistory(
    historyState: PriceHistoryState,
    modifier: Modifier = Modifier
) {

    val priceHistory: List<CoinPrice> = historyState.history?.priceHistory ?: emptyList()
    val lineColor = MaterialTheme.colors.onBackground

    if (historyState.error.isBlank()) {
        Card(
            shape = RoundedCornerShape(4.dp),
            elevation = 12.dp,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize(align = Alignment.BottomStart)
            ) {
                Canvas(modifier = modifier) {
                    // Graph variables
                    val total = priceHistory.size
                    val maxValue: Float = historyState.history?.priceMax?.toFloat() ?: 0f
                    val lineDistance = size.width / (total - 1)
                    val canvasHeight = size.height
                    var currentLineDistance = 0f

                    priceHistory.forEachIndexed { index, coinPrice ->
                        if (total >= index + 2) {
                            drawLine(
                                start = Offset(
                                    x = currentLineDistance,
                                    y = ((maxValue - coinPrice.price.toFloat()) * (canvasHeight / maxValue))
                                ),
                                end = Offset(
                                    x = currentLineDistance + lineDistance,
                                    y = ((maxValue - priceHistory[index + 1].price.toFloat()) * (canvasHeight / maxValue))
                                ),
                                color = lineColor,
                                strokeWidth = 8f
                            )

                        }
                        currentLineDistance += lineDistance
                    }
                }
            }
        }
    }

    if (historyState.error.isNotBlank()) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = historyState.error,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
    }
}
