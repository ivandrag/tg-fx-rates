package ro.dragosivanov.tgo.di
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ro.dragosivanov.tgo.domain.api.ForeignExchangeApi

class AppContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://my.transfergo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ForeignExchangeApi::class.java)
}
