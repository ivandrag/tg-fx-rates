package ro.dragosivanov.tgo.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.dragosivanov.tgo.domain.model.Country
import ro.dragosivanov.tgo.domain.usecase.ForeignExchangeUseCase

class CurrencySelectorDialogViewModel(
    private val foreignExchangeUseCase: ForeignExchangeUseCase
) : ViewModel() {

    private val _onEvent = MutableLiveData<OnEvent>()

    val onEvent: LiveData<OnEvent>
        get() = _onEvent

    fun getCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        val currencyList = foreignExchangeUseCase.getCurrencies()
        _onEvent.postValue(OnEvent.AllCurrencies(currencyList))
    }

    sealed class OnEvent {
        data class AllCurrencies(val currencyList: List<Country>) : OnEvent()
    }
}
