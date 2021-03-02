package ro.dragosivanov.tgo.ui.foreignexchange

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ro.dragosivanov.tgo.MainApplication
import ro.dragosivanov.tgo.R
import ro.dragosivanov.tgo.di.AppContainer
import ro.dragosivanov.tgo.di.ForeignExchangeContainer
import ro.dragosivanov.tgo.di.ForeignExchangeViewModelFactory
import ro.dragosivanov.tgo.ui.SharedViewModel

class ForeignExchangeFragment : Fragment(R.layout.fragment_foreign_exchange) {

    private lateinit var foreignExchangeViewModel: ForeignExchangeViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var appContainer: AppContainer
    private lateinit var convertButton: Button
    private lateinit var fromTextView: TextView
    private lateinit var toTextView: TextView
    private lateinit var amountEditText: EditText
    private lateinit var convertedAmountTitleTextView: TextView
    private lateinit var convertedAmountEditText: EditText

    private val onEventObserver = Observer<ForeignExchangeViewModel.OnEvent> {
        when (it) {
            is ForeignExchangeViewModel.OnEvent.Success -> {
                println(it.exchangedCurrency)
                convertedAmountTitleTextView.visibility = View.VISIBLE
                convertedAmountEditText.visibility = View.VISIBLE
                fromTextView.text = it.exchangedCurrency.from
                toTextView.text = it.exchangedCurrency.to
                amountEditText.setText(it.exchangedCurrency.fromAmount.toString())
                convertedAmountEditText.setText(it.exchangedCurrency.toAmount.toString())
            }
        }
    }

    private val onItemClickObserver = Observer<SharedViewModel.OnEvent> {
        when (it) {
            is SharedViewModel.OnEvent.ItemClick -> {
                fromTextView.text = it.code
                val flagImage = activity?.resources?.getDrawable(it.flag)
                fromTextView.setCompoundDrawablesWithIntrinsicBounds(flagImage, null, null, null)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foreignExchangeViewModel = ViewModelProvider(
            this,
            initDependencyInjection()
        ).get(ForeignExchangeViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        registerObservers()
        initViews()

        convertButton.setOnClickListener {
            val from = fromTextView.text.toString()
            val to = toTextView.text.toString()
            val amount = amountEditText.text.toString().toFloat()
            foreignExchangeViewModel.exchangeCurrencyRates(from, to, amount)
        }

        fromTextView.setOnClickListener {
            findNavController().navigate(R.id.startCurrencySelectorDialog)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appContainer.foreignExchangeContainer = null
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

    private fun registerObservers() {
        foreignExchangeViewModel.onEvent.observe(viewLifecycleOwner, onEventObserver)
        sharedViewModel.onEvent.observe(viewLifecycleOwner, onItemClickObserver)
    }

    private fun initViews() {
        convertButton = activity?.findViewById(R.id.convert_button) as Button
        fromTextView = activity?.findViewById(R.id.from_text_view) as TextView
        toTextView = activity?.findViewById(R.id.to_text_view) as TextView
        amountEditText = activity?.findViewById(R.id.amount_edit_text) as EditText
        convertedAmountTitleTextView =
            activity?.findViewById(R.id.converted_amount_title_text_view) as TextView
        convertedAmountEditText =
            activity?.findViewById(R.id.converted_amount_edit_text) as EditText
    }
}