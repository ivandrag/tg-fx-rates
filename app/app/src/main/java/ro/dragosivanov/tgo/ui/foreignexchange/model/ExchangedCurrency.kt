package ro.dragosivanov.tgo.ui.foreignexchange.model

data class ExchangedCurrency(
    val from: String,
    val to: String,
    val rate: Float,
    val fromAmount: Float,
    val toAmount: Float
)
