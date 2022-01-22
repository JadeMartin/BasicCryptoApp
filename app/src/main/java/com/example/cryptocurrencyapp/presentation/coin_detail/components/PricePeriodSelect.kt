package com.example.cryptocurrencyapp.presentation.coin_detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.common.Constants

@Composable
fun PricePeriodSelect(
    modifier: Modifier = Modifier,
    pricePeriod: Long,
    onDateChange: (Long) -> Unit
) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        DefaultRadioButton(text = stringResource(R.string.one_week),
            selected = pricePeriod == Constants.DAYS_IN_A_WEEK,
            onSelect = {
                onDateChange(Constants.DAYS_IN_A_WEEK)
            })
        Spacer(modifier = Modifier.width(10.dp))
        DefaultRadioButton(text = stringResource(R.string.one_month),
            selected = pricePeriod == Constants.DAYS_IN_A_MONTH,
            onSelect = {
                onDateChange(Constants.DAYS_IN_A_MONTH)
            })
        Spacer(modifier = Modifier.width(10.dp))
        DefaultRadioButton(text = stringResource(R.string.one_year),
            selected = pricePeriod == Constants.DAYS_IN_A_YEAR,
            onSelect = {
                onDateChange(Constants.DAYS_IN_A_YEAR)
            })
    }
}