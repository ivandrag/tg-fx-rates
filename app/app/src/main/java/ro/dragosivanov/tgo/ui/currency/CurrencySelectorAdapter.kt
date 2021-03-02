package ro.dragosivanov.tgo.ui.currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ro.dragosivanov.tgo.R
import ro.dragosivanov.tgo.domain.model.Country

class CurrencySelectorAdapter(private val onItemClick: (String, Int) -> Unit) :
    RecyclerView.Adapter<CurrencySelectorAdapter.ViewHolder>() {

    val countryList = mutableListOf<Country>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country_code, parent, false),
        onItemClick
    )

    override fun getItemCount() = countryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countryList[position]
        holder.bindData(country)
    }

    class ViewHolder(private val view: View, private val onItemClick: (String, Int) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private lateinit var currencyFlagImageView: ImageView
        private lateinit var currencyCodeTextView: TextView

        fun bindData(country: Country) {
            currencyFlagImageView = view.findViewById(R.id.flag_image_view)
            currencyCodeTextView = view.findViewById(R.id.currency_text_view)
            currencyFlagImageView.setBackgroundResource(country.flag)
            currencyCodeTextView.text = country.code
            view.setOnClickListener {
                onItemClick(country.code, country.flag)
            }
        }
    }
}
