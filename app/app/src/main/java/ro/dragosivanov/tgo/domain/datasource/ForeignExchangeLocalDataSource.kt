package ro.dragosivanov.tgo.domain.datasource

import ro.dragosivanov.tgo.domain.db.Countries

class ForeignExchangeLocalDataSource {

    fun getCurrencies() = Countries.getCurrencies()
}
