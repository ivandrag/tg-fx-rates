package ro.dragosivanov.tgo.ui.foreignexchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.dragosivanov.tgo.domain.usecase.ForeignExchangeUseCase
import ro.dragosivanov.tgo.ui.foreignexchange.model.ExchangedCurrency

class ForeignExchangeViewModel(
    private val foreignExchangeUseCase: ForeignExchangeUseCase
): ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()

    val onEvent: LiveData<OnEvent>
        get() = _onEvent

    fun exchangeCurrencyRates(from: String, to: String, amount: Float) =
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                foreignExchangeUseCase.exchangeCurrencyRates(from, to, amount)
            }.onSuccess { exchangedCurrency ->
                _onEvent.postValue(OnEvent.Success(exchangedCurrency))
            }.onFailure {
                // Do Something with the failure
            }
        }

    sealed class OnEvent {
        data class Success(val exchangedCurrency: ExchangedCurrency) : OnEvent()
    }
}
