package com.example.cryptocurrencyapp.presentation.coin_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptocurrencyapp.common.Constants

@Composable
fun DateWindowSection(
    modifier: Modifier = Modifier,
    dateWindow: Long,
    onDateChange: (Long) -> Unit
) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        DefaultRadioButton(text = "1W",
            selected = dateWindow == Constants.TIME_WEEK,
            onSelect = {
                onDateChange(Constants.TIME_WEEK)
            })
        Spacer(modifier = Modifier.width(10.dp))
        DefaultRadioButton(text = "1M",
            selected = dateWindow == Constants.TIME_MONTH,
            onSelect = {
                onDateChange(Constants.TIME_MONTH)
            })
        Spacer(modifier = Modifier.width(10.dp))
        DefaultRadioButton(text = "1Y",
            selected = dateWindow == Constants.TIME_YEAR,
            onSelect = {
                onDateChange(Constants.TIME_YEAR)
            })
    }
}