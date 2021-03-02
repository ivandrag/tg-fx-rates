package ro.dragosivanov.tgo.ui.foreignexchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.dragosivanov.tgo.domain.usecase.ForeignExchangeUseCase

class ForeignExchangeViewModel(
    private val foreignExchangeUseCase: ForeignExchangeUseCase
): ViewModel() {

    fun exchangeCurrencyRates(from: String, to: String, amount: Float) = viewModelScope.launch {
        val response = foreignExchangeUseCase.exchangeCurrencyRates(from, to, amount)
        println(response)
    }
}
