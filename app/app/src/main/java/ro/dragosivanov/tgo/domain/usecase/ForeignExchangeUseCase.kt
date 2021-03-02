package ro.dragosivanov.tgo.domain.usecase

import ro.dragosivanov.tgo.domain.repository.ForeignExchangeRepository

class ForeignExchangeUseCase(
    private val foreignExchangeRepository: ForeignExchangeRepository
) {

    suspend fun exchangeCurrencyRates(from: String, to: String, amount: Float) =
        foreignExchangeRepository.exchangeCurrencyRates(from, to, amount)

    fun getCurrencies() = foreignExchangeRepository.getCurrencies()
}
