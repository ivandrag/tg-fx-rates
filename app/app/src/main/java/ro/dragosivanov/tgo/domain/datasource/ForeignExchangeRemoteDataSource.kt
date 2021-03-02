package ro.dragosivanov.tgo.domain.datasource

import ro.dragosivanov.tgo.domain.api.ForeignExchangeApi

class ForeignExchangeRemoteDataSource(
    private val foreignExchangeApi: ForeignExchangeApi
) {

    suspend fun exchangeCurrencyRates(from: String, to: String, amount: Float) =
        foreignExchangeApi.exchangeCurrencyRates(from, to, amount)
}
