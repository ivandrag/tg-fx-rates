package ro.dragosivanov.tgo.ui.foreignexchange

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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
    private lateinit var convertButton: Button
    private lateinit var fromTextView: TextView
    private lateinit var toTextView: TextView
    private lateinit var amountEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foreignExchangeViewModel = ViewModelProvider(
            this,
            initDependencyInjection()
        ).get(ForeignExchangeViewModel::class.java)

        convertButton = activity?.findViewById(R.id.convert_button) as Button
        fromTextView = activity?.findViewById(R.id.from_text_view) as TextView
        toTextView = activity?.findViewById(R.id.to_text_view) as TextView

        convertButton.setOnClickListener {
            val from = fromTextView.text.toString()
            val to = toTextView.text.toString()
            val amount = amountEditText.text.toString().toFloat()
            foreignExchangeViewModel.exchangeCurrencyRates(from, to, amount)
        }
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