package ro.dragosivanov.tgo.domain.model

data class ExchangeResponse(
    val from: String,
    val to: String,
    val rate: Float,
    val fromAmount: Float,
    val toAmount: Float

)