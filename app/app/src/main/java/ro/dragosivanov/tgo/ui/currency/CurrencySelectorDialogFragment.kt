package ro.dragosivanov.tgo.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ro.dragosivanov.tgo.MainApplication
import ro.dragosivanov.tgo.R
import ro.dragosivanov.tgo.di.AppContainer
import ro.dragosivanov.tgo.di.CurrencySelectorDialogContainer
import ro.dragosivanov.tgo.di.ForeignExchangeViewModelFactory

class CurrencySelectorDialogFragment : DialogFragment() {

    private lateinit var currencySelectorDialogViewModel: CurrencySelectorDialogViewModel
    private lateinit var appContainer: AppContainer

    private val onEventObserver = Observer<CurrencySelectorDialogViewModel.OnEvent> {
        when(it) {
            is CurrencySelectorDialogViewModel.OnEvent.AllCurrencies -> {
                println(it.currencyList)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_selector_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencySelectorDialogViewModel = ViewModelProvider(
            this,
            initDependencyInjection()
        ).get(CurrencySelectorDialogViewModel::class.java)
        registerObservers()
        currencySelectorDialogViewModel.getCurrencies()
    }

    override fun onDestroy() {
        super.onDestroy()
        appContainer.currencySelectorDialogContainer = null
    }

    private fun initDependencyInjection(): ForeignExchangeViewModelFactory {
        appContainer = (activity?.application as MainApplication).appContainer
        appContainer.currencySelectorDialogContainer =
            CurrencySelectorDialogContainer(appContainer.foreignExchangeApiService)
        appContainer.foreignExchangeContainer?.let {
            val foreignExchangeUseCase = it.foreignExchangeUseCase
            return ForeignExchangeViewModelFactory(foreignExchangeUseCase)
        }
        throw Exception("ForeignExchangeContainer cannot be null")
    }

    private fun registerObservers() {
        currencySelectorDialogViewModel.onEvent.observe(this, onEventObserver)
    }
}
