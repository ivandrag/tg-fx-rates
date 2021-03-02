package ro.dragosivanov.tgo.domain.repository

import ro.dragosivanov.tgo.domain.datasource.ForeignExchangeLocalDataSource
import ro.dragosivanov.tgo.domain.datasource.ForeignExchangeRemoteDataSource

class ForeignExchangeRepository(
    private val foreignExchangeRemoteDataSource: ForeignExchangeRemoteDataSource,
    private val foreignExchangeLocalDataSource: ForeignExchangeLocalDataSource
) {

    suspend fun exchangeCurrencyRates(from: String, to: String, amount: Float) =
        foreignExchangeRemoteDataSource.exchangeCurrencyRates(from, to, amount)

    fun getCurrencies() = foreignExchangeLocalDataSource.getCurrencies()
}
