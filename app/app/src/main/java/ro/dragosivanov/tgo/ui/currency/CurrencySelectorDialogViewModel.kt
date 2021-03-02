package ro.dragosivanov.tgo.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ro.dragosivanov.tgo.domain.model.Country
import ro.dragosivanov.tgo.domain.usecase.ForeignExchangeUseCase

class CurrencySelectorDialogViewModel(
    private val foreignExchangeUseCase: ForeignExchangeUseCase
) : ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()

    val onEvent: LiveData<OnEvent>
        get() = _onEvent

    fun getCurrencies() {
        val currencyList = foreignExchangeUseCase.getCurrencies()
        _onEvent.value = OnEvent.AllCurrencies(currencyList)
    }

    sealed class OnEvent {
        data class AllCurrencies(val currencyList: List<Country>) : OnEvent()
    }
}
