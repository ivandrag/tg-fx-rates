package ro.dragosivanov.tgo.domain.db

import ro.dragosivanov.tgo.R
import ro.dragosivanov.tgo.domain.model.Country

object Countries {
    fun getCurrencies(): List<Country> {
        val countryList = mutableListOf<Country>()
        countryList.add(Country("EUR", R.drawable.ic_flag_us))
        countryList.add(Country("GBP", R.drawable.ic_flag_gb))
        countryList.add(Country("RUB", R.drawable.ic_flag_ru))
        countryList.add(Country("PLN", R.drawable.ic_flag_pl))
        countryList.add(Country("RON", R.drawable.ic_flag_ro))
        countryList.add(Country("UAH", R.drawable.ic_flag_ua))
        return countryList
    }
}
