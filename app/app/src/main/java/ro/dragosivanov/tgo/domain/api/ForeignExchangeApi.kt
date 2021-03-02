package ro.dragosivanov.tgo.domain.api

import retrofit2.http.GET
import retrofit2.http.Query
import ro.dragosivanov.tgo.domain.model.ExchangeResponse

interface ForeignExchangeApi {

    @GET("/api/fx-rates")
    suspend fun exchangeCurrencyRates(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Float
    ): ExchangeResponse
}
