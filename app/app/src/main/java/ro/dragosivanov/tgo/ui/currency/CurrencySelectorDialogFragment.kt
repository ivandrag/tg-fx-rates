package ro.dragosivanov.tgo.ui.currency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.dragosivanov.tgo.MainApplication
import ro.dragosivanov.tgo.R
import ro.dragosivanov.tgo.di.AppContainer
import ro.dragosivanov.tgo.di.CurrencySelectorDialogContainer
import ro.dragosivanov.tgo.di.ForeignExchangeViewModelFactory
import ro.dragosivanov.tgo.ui.SharedViewModel

class CurrencySelectorDialogFragment : DialogFragment() {

    private lateinit var currencySelectorDialogViewModel: CurrencySelectorDialogViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var appContainer: AppContainer
    private lateinit var currencySelectorAdapter: CurrencySelectorAdapter
    private lateinit var countryListRecyclerView: RecyclerView

    private val onEventObserver = Observer<CurrencySelectorDialogViewModel.OnEvent> {
        when (it) {
            is CurrencySelectorDialogViewModel.OnEvent.AllCurrencies -> {
                countryListRecyclerView.adapter = currencySelectorAdapter
                countryListRecyclerView.layoutManager = LinearLayoutManager(activity)
                currencySelectorAdapter.countryList.addAll(it.currencyList)
                currencySelectorAdapter.notifyDataSetChanged()
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
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        registerObservers()
        countryListRecyclerView = activity?.findViewById(R.id.country_list_recyclerView) as RecyclerView
        currencySelectorAdapter = CurrencySelectorAdapter { code, flag ->
            sharedViewModel.onItemClick(code, flag)
            findNavController().popBackStack()
        }
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
        currencySelectorDialogViewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)
    }
}
