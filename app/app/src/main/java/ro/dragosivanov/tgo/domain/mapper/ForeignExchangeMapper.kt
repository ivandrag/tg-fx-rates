package ro.dragosivanov.tgo.domain.mapper

import ro.dragosivanov.tgo.domain.model.ExchangeResponse
import ro.dragosivanov.tgo.ui.foreignexchange.model.ExchangedCurrency

fun ExchangeResponse.toExchangedCurrency() = ExchangedCurrency(
    this.from, this.to, this.rate, this.fromAmount, this.toAmount
)
