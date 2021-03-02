package ro.dragosivanov.tgo.ui.foreignexchange

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ro.dragosivanov.tgo.MainApplication
import ro.dragosivanov.tgo.R
import ro.dragosivanov.tgo.di.AppContainer
import ro.dragosivanov.tgo.di.ForeignExchangeContainer
import ro.dragosivanov.tgo.di.ForeignExchangeViewModelFactory

class ForeignExchangeFragment : Fragment(R.layout.fragment_foreign_exchange) {

    private lateinit var foreignExchangeViewModel: ForeignExchangeViewModel
    private lateinit var appContainer: AppContainer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foreignExchangeViewModel = ViewModelProvider(
            this,
            initDependencyInjection()
        ).get(ForeignExchangeViewModel::class.java)
        foreignExchangeViewModel.exchangeCurrencyRates("EUR", "USD", 323F)
    }

    private fun initDependencyInjection(): ForeignExchangeViewModelFactory {
        appContainer = (activity?.application as MainApplication).appContainer
        appContainer.foreignExchangeContainer =
            ForeignExchangeContainer(appContainer.foreignExchangeApiService)
        appContainer.foreignExchangeContainer?.let {
            val foreignExchangeUseCase = it.foreignExchangeUseCase
            return ForeignExchangeViewModelFactory(foreignExchangeUseCase)
        }
        throw Exception("ForeignExchangeContainer cannot be null")
    }

    override fun onDestroy() {
        super.onDestroy()
        appContainer.foreignExchangeContainer = null
    }
}